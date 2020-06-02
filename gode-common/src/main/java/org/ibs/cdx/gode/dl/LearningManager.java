package org.ibs.cdx.gode.dl;

import org.apache.commons.io.FilenameUtils;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.ibs.cdx.gode.exception.ClassicRuntimeException;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class LearningManager<T extends LearningRequired<T,Characteristic>,Characteristic, R extends Learned> implements IWitch<T,Characteristic,R>{

    protected LearningArgument args;
    private AtomicBoolean trainingComplete = new AtomicBoolean(false);

    public LearningManager(LearningArgument args){
        this.args = args;
    }

    private void normaliseTrainingData(){
        if(isNormalisationRequired()){
            DataNormalization normalizer = new NormalizerStandardize();
            DataSet dataSet = args.get(LearningArgument.Default.TRAINING_DATA);
            args.add(LearningArgument.Default.NORMALISER, normalizer);
            normalizer.fit(dataSet);
            normalizer.transform(dataSet);
        }
    }

    private void normaliseTestData(){
        if(isNormalisationRequired()){
            DataNormalization normalizer = args.get(LearningArgument.Default.NORMALISER);
            DataSet testData = args.get(LearningArgument.Default.TRAINING_DATA);
            normalizer.transform(testData);
        }
    }
    private Map<Integer, T> objectify(DataSet testData) {
        Map<Integer, T> objectiveData = new HashMap<>();
        INDArray features = testData.getFeatureMatrix();
        for (int i = 0; i < features.rows(); i++) {
            INDArray slice = features.slice(i);
            T obj = transform(slice);
            objectiveData.put(i, obj);
        }
        return objectiveData;
    }

    @Override
    public void trainingData(String handle, MultipartFile file)  {
        try {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String uuid = UUID.randomUUID().toString();
            File test = new File("dataset".concat(uuid));
            file.transferTo(test);
            if(extension == "csv"){
                Long data = args.get(LearningArgument.Default.TRAINING_DATA_BATCH);
                if(data != null ) data = Files.lines(test.toPath()).count();
                DataSet trainingData = readCSVDataset(test, data.intValue(), analysedFields().size(), characters().size());
                args.add(LearningArgument.Default.TRAINING_DATA, trainingData);
            }
            CompletableFuture.runAsync(this::trainInternal);
        } catch (IOException e) {
            throw new ClassicRuntimeException("Dataset upload failed",e);
        }
    }

    public DataSet readCSVDataset(File csvFile, int batchSize, int labelIndex, int numClasses){
        try {
            RecordReader rr = new CSVRecordReader();
            rr.initialize(new FileSplit(csvFile));
            DataSetIterator iterator = new RecordReaderDataSetIterator(rr, batchSize, labelIndex, numClasses);
            return iterator.next();
        } catch (Exception e) {
            throw new ClassicRuntimeException("CSV Dataset upload failed",e);
        }

    }

    @Override
    public void testData(String handle, MultipartFile file) {
        try {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String uuid = UUID.randomUUID().toString();
            File test = new File("dataset".concat(uuid));
            file.transferTo(test);
            if(extension == "csv"){
                Long data = Files.lines(test.toPath()).count();
                DataSet testData = readCSVDataset(test, data.intValue(), analysedFields().size(),characters().size());
                args.add(LearningArgument.Default.TEST_DATA, testData);
            }
        } catch (IOException e) {
            throw new ClassicRuntimeException("Dataset upload failed",e);
        }
    }

    private Map<Integer, R> classify(INDArray output) {
        Map<Integer, R> results = new HashMap<>();
        for (int i = 0; i < output.rows(); i++) {
            R r = newResult();
            r.setCharacter(characters().get(max(sliceToArray(output.slice(i)))));
            results.put(i, r);
        }
        return results;
    }

    private float[] sliceToArray(INDArray rowSlice) {
        float[] result = new float[rowSlice.columns()];
        for (int i = 0; i < rowSlice.columns(); i++) {
            result[i] = rowSlice.getFloat(i);
        }
        return result;
    }

    private static int max(float[] vals) {
        int maxIndex = 0;
        for (int i = 1; i < vals.length; i++) {
            float newnumber = vals[i];
            if ((newnumber > vals[maxIndex])) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }


    public void trainInternal() {
        trainingComplete.compareAndSet(true, false);
        DataSet trainingData = args.get(LearningArgument.Default.TRAINING_DATA);
        if(this.isNormalisationRequired()){
            normaliseTrainingData();
        }
        trainingModel();
        MultiLayerNetwork model = args.get(LearningArgument.Default.MODEL);
        model.init();
        model.setListeners(new ScoreIterationListener(100));
        model.fit(trainingData);
        trainingComplete.compareAndSet(false, true);
    }

    public Map<Integer, R> classify() {
        return classifyInternal(1);
    }

    public Map<Integer, R> classifyInternal(int count) {
        if(count >= 4){
            throw new ClassicRuntimeException("Retry done for 3 times, training is not yet complete");
        }
        if(this.trainingComplete.get()) {
            DataSet testData = args.get(LearningArgument.Default.TEST_DATA);
            if (this.isNormalisationRequired()) {
                normaliseTestData();
            }
            MultiLayerNetwork model = args.get(LearningArgument.Default.MODEL);
            INDArray output = model.output(testData.getFeatureMatrix());
            Evaluation eval = new Evaluation(3);
            eval.eval(testData.getLabels(), output);
           return classify(output);
        }
        return classifyInternal(count+1);
    }

    public abstract R newResult();

    public List<String> characters() {
        return newResult().characters();
    }


    public List<String> analysedFields() {
        return newResult().analysedFields();
    }

}
