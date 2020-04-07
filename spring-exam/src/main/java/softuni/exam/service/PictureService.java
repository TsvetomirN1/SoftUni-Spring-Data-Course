package softuni.exam.service;


import softuni.exam.models.entities.Picture;

import java.io.IOException;
import java.util.List;


public interface PictureService {

    boolean areImported();

    String readPicturesFromFile() throws IOException;
	
	String importPictures() throws IOException;

    List<Picture> findByCar_Id(Long id);

}
