package org.ibs.cdx.gode.codegen.velocity.entity.persistence;

public class PersistenceQualification {

    public Level getWrite() {
        return write;
    }

    public void setWrite(Level write) {
        this.write = write;
    }

    public Level getRead() {
        return read;
    }

    public void setRead(Level read) {
        this.read = read;
    }

    public Level getRelativeRead() {
        return relativeRead;
    }

    public void setRelativeRead(Level relativeRead) {
        this.relativeRead = relativeRead;
    }

    public boolean isTransactional() {
        return transactional;
    }

    public void setTransactional(boolean transactional) {
        this.transactional = transactional;
    }

    private Level write;
    private Level read;
    private Level relativeRead;
    private boolean transactional;

}
