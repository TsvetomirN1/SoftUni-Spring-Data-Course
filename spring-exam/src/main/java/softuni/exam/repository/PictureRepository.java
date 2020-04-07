package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.models.entities.Picture;

import java.util.List;


public interface PictureRepository extends JpaRepository<Picture, Long> {

    Picture findByName(String name);

    List<Picture> findByCar_Id(Long id);

}
