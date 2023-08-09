package model.json.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import enums.ParamEnums.Gender;

public class MemberGenderSerializer extends JsonSerializer<Gender> {

	@Override
	public void serialize(Gender gender, JsonGenerator generator, SerializerProvider provider) throws IOException {
		switch(gender) {
		case FEMALE:
			generator.writeString("女性");
			break;
		case MALE:
			generator.writeString("男性");
			break;
		default:
			break;
		}
	}
}
