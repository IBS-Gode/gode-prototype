package org.ibs.cdx.gode.dl;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IWitch<Request,Character, Response> {

    void trainingData(String handle, MultipartFile file);
    void testData(String handle, MultipartFile file);
    void trainInternal( );
    Map<Integer, Response> classifyInternal();
    void trainingModel( );
    Request transform(INDArray slice);

    default boolean isNormalisationRequired(){
        return true;
    }
}
