package org.ednovo.gooru.client.mvp.library.partner;

import org.ednovo.gooru.application.shared.model.content.ThumbnailDo;
import org.ednovo.gooru.application.shared.model.library.CourseDo;
import org.ednovo.gooru.application.shared.model.library.LibraryUserDo;

public interface LoadBannerActionInterface {
	
	/**
	 *Interface to set the library banner image. 
	 *
	 */
	interface setLibraryBanner{
		public void loadLibBanner(CourseDo courseDo, ThumbnailDo thumbnailDo, LibraryUserDo libraryUserDo);
		public CourseDo getUpdatedCourseDo();
	}

}
