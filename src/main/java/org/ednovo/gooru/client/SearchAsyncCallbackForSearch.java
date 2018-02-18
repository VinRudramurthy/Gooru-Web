package org.ednovo.gooru.client;

import java.io.IOException;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.exception.ServerDownException;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.search.AbstractSearchDo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class SearchAsyncCallbackForSearch<T extends AbstractSearchDo<?>> implements AsyncCallback<T>{
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	@Override
	public void onFailure(Throwable caught) {
		String message = "";
		if(caught instanceof ServerDownException){
			ServerDownException serverDownException=(ServerDownException)caught;
			AppClientFactory.getInjector().getHomeService().getRedirectServerUrl(new AsyncCallback<String>() {
				@Override
				public void onSuccess(String redirectUrl) {
					Window.open(redirectUrl, "_self","");
				}
				@Override
				public void onFailure(Throwable caught) {
				}
			});
		}
		else if (caught instanceof IOException) {
			Window.Location.reload();
		} else {
			if (caught instanceof GwtException && ((GwtException) caught).getErrors().size() > 0) {
				message = ((GwtException) caught).getMessage();
			} else if (caught.getMessage() != null) {
				if (caught.getMessage().contains("Login")) {
					message = i18n.GL1096();
				}
				if (caught.getMessage().contains("403")) {
					message = i18n.GL1095();
				} else if (caught.getMessage().contains("CODE_502")) {
					message = i18n.GL0695();
				} else {
					message = caught.getMessage();
				}
			} else {
				message = i18n.GL0839();
			}
			if (message.trim().toString().equalsIgnoreCase("0")){

			}else{
				//new AlertContentUc(i18n.GL0844, message);
			}
		}

	}

	@Override
	public final void onSuccess(T result) {
		onCallSuccess(result);
	}

	public final void execute(T searchDo) {
		run(searchDo);
	}

	protected abstract void run(T searchDo);

	public abstract void onCallSuccess(T result);
}
