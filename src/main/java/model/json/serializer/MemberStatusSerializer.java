package model.json.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import enums.ParamEnums.MemberStatus;

public class MemberStatusSerializer extends JsonSerializer<MemberStatus> {

	@Override
	public void serialize(MemberStatus status, JsonGenerator generator, SerializerProvider provider) throws IOException {
		switch(status) {
		case ACTIVE:
			generator.writeString("有効");
			break;
		case INACTIVE:
			generator.writeString("退会");
			break;
		default:
			break;
		}
	}
}
