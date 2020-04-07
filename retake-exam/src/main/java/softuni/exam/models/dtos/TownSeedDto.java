package softuni.exam.models.dtos;


import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TownSeedDto {

    @Expose
    private String name;
    @Expose
    private Integer population;
    @Expose
    private String guide;

    public TownSeedDto() {
    }

    @NotNull
    @Column(name = "name", unique = true)
    @Length(min = 2)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Min(value = 0)
    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    @NotNull
    @Length(max = 600)
    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }
}
