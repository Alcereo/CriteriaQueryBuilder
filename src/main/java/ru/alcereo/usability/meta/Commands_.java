package ru.alcereo.usability.meta;

import ru.alcereo.entities.CommandsEntity;
import ru.alcereo.entities.ParametersEntity;
import ru.alcereo.usability.Attributive;

/**
 * Created by alcereo on 28.04.17.
 */
public class Commands_ {

    static Attributive<?,CommandsEntity> table(){
        Attributive<?, CommandsEntity> result = new Attributive<>();
        result.setViewName(CommandsEntity.class.getName());

        return result;
    }



}
