package com.fcs.mongodbsaveimage;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

/**
 * @author Fernando
 *
 */
public class MongoDBSaveImage {

    public static void main(String[] args) {

        try {
            
            System.out.println("Inicio...");

            Mongo mongo = new Mongo("localhost", 27017);
            DB db = mongo.getDB("fotosdb");
            DBCollection collection = db.getCollection("imagens");

            File imageFile = new File("/home/fernando/Downloads/microservice_architecture.png");

            // cria um namespace para armazenar as "fotos"
            GridFS gfsPhoto = new GridFS(db, "fotos");

            // pega a imagem do disco
            GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);

            // seta o nome para identificar a imagem
            gfsFile.setFilename(imageFile.getName());

            // salva a imagem no mongoDB
            gfsFile.save();

            // imprime o resultado
            DBCursor cursor = gfsPhoto.getFileList();
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }

            // pega a imagem do mongoDB pelo seu nome
            GridFSDBFile imageForOutput = gfsPhoto.findOne(imageFile.getName());

            // save a imagem no disco 
            imageForOutput.writeTo("/home/fernando/Downloads/microservice_architecture2.png");

            // remove a imagem do mongoDB
            //gfsPhoto.remove(gfsPhoto.findOne(imageFile.getName()));
            
            System.out.println("Fim!!");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
}
