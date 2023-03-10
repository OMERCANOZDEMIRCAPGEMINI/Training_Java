package com.capgemini.training.mappers;

import com.capgemini.training.dtos.PersonDTO;
import com.capgemini.training.models.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper( PersonMapper.class );

    PersonDTO personToPersonDto(Person person);
    Person personDtoToPerson(PersonDTO personDTO);
}
