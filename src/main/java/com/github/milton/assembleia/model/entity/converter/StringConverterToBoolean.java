package com.github.milton.assembleia.model.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringConverterToBoolean implements AttributeConverter<String, Boolean> {

	@Override
	public Boolean convertToDatabaseColumn(String attribute) {
		String value = attribute.toLowerCase();
		return (attribute != null && value.equals("sim")) ? Boolean.TRUE : Boolean.FALSE;   
	}

	@Override
	public String convertToEntityAttribute(Boolean dbData) {
		return Boolean.TRUE.equals(dbData) ? "Sim" : "Não";
	}

	
	
}
