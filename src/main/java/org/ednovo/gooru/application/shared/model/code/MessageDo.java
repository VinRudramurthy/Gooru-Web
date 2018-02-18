package org.ednovo.gooru.application.shared.model.code;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class MessageDo implements IsSerializable
{
	private static final long serialVersionUID = 1L;

	public MessageDo(){

		}
}
