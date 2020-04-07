package softuni.exam.util;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface XMLParser {

    <T> T unmarshalFromFile(String filePath, Class<T> tClass) throws JAXBException, FileNotFoundException;


}
