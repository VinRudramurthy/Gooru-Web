package org.ednovo.gooru.application.server.deserializer;

import org.ednovo.gooru.application.server.serializer.JsonDeserializer;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryListDo;
import org.restlet.ext.json.JsonRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class ProfileLibraryDeserializer extends DeSerializer{

	private static final String SEARCH_RESULTS = "searchResult";
	private static final String COUNT = "count";
	private static final String COLLECTION_ITEMS = "collectionItems";
	private static final String TITLE = "title";
	private static final String GOALS = "goals";
	private static final String GOORUOID = "gooruOid";
	private static final String THUMBNAILS = "thumbnails";
	private static final String FOLDER = "folder";
	private static final String TYPE = "type";

	private static final Logger logger = LoggerFactory.getLogger(ProfileLibraryDeserializer.class);

	public ProfileLibraryListDo deserializeFolderList(JsonRepresentation jsonRep) {
			try {
				if (jsonRep != null && jsonRep.getSize() != -1) {
					return JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), new TypeReference<ProfileLibraryListDo>() {});
				}
			} catch (Exception e) {
				logger.error("Exception::", e);
			}
			return new ProfileLibraryListDo();
	}
}