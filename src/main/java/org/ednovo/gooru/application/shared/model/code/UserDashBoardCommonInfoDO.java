package org.ednovo.gooru.application.shared.model.code;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class UserDashBoardCommonInfoDO implements IsSerializable
{

	private static final long serialVersionUID = 1L;

	private MessageDo message;



	private List<PublishedCollectionsInfoDo> content;

	private PaginateDo paginate;

	public UserDashBoardCommonInfoDO(){

	}

	public MessageDo getMessage() {
		return message;
	}

	public void setMessage(MessageDo message) {
		this.message = message;
	}

	public List<PublishedCollectionsInfoDo> getContent() {
		return content;
	}

	public void setContent(List<PublishedCollectionsInfoDo> content) {
		this.content = content;
	}

	public PaginateDo getPaginate() {
		return paginate;
	}

	public void setPaginate(PaginateDo paginate) {
		this.paginate = paginate;
	}

}
