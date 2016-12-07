package ru.sberbank.study.java.serialization.service;

import ru.sberbank.study.java.serialization.Cache;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

class FileCacheService extends AbstractCacheService {
    public boolean containsResult(Method method, Cache annotation, Object[] args) {
        return new File(getKeyFor(method, annotation, args)).exists();
    }

    public Object calculateAndSaveResult(Object object, Method method, Cache annotation, Object[] args)  {
        Object result = calculateResult(object, method, args);
        File cacheFile = new File(getKeyFor(method, annotation, args));
        if(!cacheFile.getParentFile().exists()) {
            cacheFile.getParentFile().mkdirs();
        }
        try(ObjectOutputStream oos = new ObjectOutputStream(getOutputStreamForFile(annotation, cacheFile))) {
            oos.writeObject(cutResultIfNecessary(result, annotation));
        } catch (IOException e) {
            logger.severe("Error writing to file: "+cacheFile.getName() + ". Check access rights to file.", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    public Object getResult(Method method, Cache annotation, Object[] args)  {
        Exception exc;
        File resultFile = new File(getKeyFor(method, annotation, args));
        try {
            return new ObjectInputStream(getInputStreamForFile(annotation, resultFile))
                    .readObject();
        } catch (IOException e) {
            logger.severe("Error during read of file "+resultFile.getName() + ". " +
                    "Check whether it's not busy by other process and check its access rights.");
            exc = e;
        } catch (ClassNotFoundException e) {
            logger.severe("Error during serialisation of results of method "+method.getName()+" " +
                    "with parameters: "+ Arrays.asList(args).toString()+" failed. " +
                    "Check whether return type of this method is serialisable.");
            exc = e;
        }
        throw new RuntimeException(exc);
    }

    private OutputStream getOutputStreamForFile(Cache annotation, File file) throws IOException {
        if(annotation.zip()) {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file));
            zos.putNextEntry(new ZipEntry("cache"));
            return zos;
        } else {
            return new FileOutputStream(file);
        }
    }

    private InputStream getInputStreamForFile(Cache annotation, File file) throws FileNotFoundException {
        if(annotation.zip()) {

        } else {
            return new FileInputStream(file);
        }
    }
}
