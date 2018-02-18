/*******************************************************************************
 * Copyright 2013 Ednovo d/b/a Gooru. All rights reserved.
 * 
 *  http://www.goorulearning.org/
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files (the
 *  "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish,
 *  distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to
 *  the following conditions:
 * 
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 * 
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/
package org.ednovo.gooru.client.mvp.library.partner;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ThumbnailDo;
import org.ednovo.gooru.application.shared.model.library.CourseDo;
import org.ednovo.gooru.application.shared.model.library.LibraryUserDo;
import org.ednovo.gooru.application.shared.model.library.PartnerFolderDo;
import org.ednovo.gooru.client.mvp.home.library.LibraryStyleBundle;
import org.ednovo.gooru.client.mvp.home.library.LibraryTopicListView;
import org.ednovo.gooru.client.mvp.home.library.LibraryUnitMenuView;
import org.ednovo.gooru.client.mvp.home.library.LibraryView;
import org.ednovo.gooru.client.util.PlayerDataLogEvents;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.Constants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
` * 
 */
public class PartnerLibraryView extends BaseViewWithHandlers<PartnerLibraryUiHandlers> implements IsPartnerLibraryView,ClientConstants{

	@UiField HTMLPanel partnerPanel;
	
	LibraryView libraryView = null;
	
	private static final String FOLDERID = "id";
	
	@UiField LibraryStyleBundle libraryStyleUc;
	
	private String unitListId = "";
	
	private CourseDo courseDo;
	
	private String placeToken;

	private static Map<String, LoadBannerActionInterface.setLibraryBanner> libraryVal;
	
	private static PartnerLibraryViewUiBinder uiBinder = GWT.create(PartnerLibraryViewUiBinder.class);

	interface PartnerLibraryViewUiBinder extends UiBinder<Widget, PartnerLibraryView> {
	}
	
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	
	static{
		libraryVal = new HashMap<String,LoadBannerActionInterface.setLibraryBanner>();
		libraryVal = addAllPartnerLibraries();
	}
	
	/**
	 * Class constructor
	 */
	public PartnerLibraryView() {
		setWidget(uiBinder.createAndBindUi(this));
		libraryView = new LibraryView(PlaceTokens.DISCOVER);
		partnerPanel.add(libraryView);
		partnerPanel.getElement().setId("pnlPartnerPanel");
		setPlaceToken(AppClientFactory.getCurrentPlaceToken());
	}

	@Override
	public void loadPartnersPage(String callBack, String placeToken) {
		libraryView.loadContributorsPage(callBack,placeToken);
	}
	

	/**
	 * Sets the library unit data. receiving from an API.
	 */
	@Override
	public void setUnitList(final ArrayList<PartnerFolderDo> folderList) {
		int listofFoldres = folderList.size();
		long uiStartTime = System.currentTimeMillis();
		if(folderList.size()==0 && PlaceTokens.ASPIRE_EPACS.equals(AppClientFactory.getCurrentPlaceToken())){
			loadingPanel(false);
			getComingSoonText(true);
		}else{
			getComingSoonText(false);
		}
		setCourseImageData();
		libraryView.getLeftNav().clear();
		libraryView.getContentScroll().clear();
		String folderId = AppClientFactory.getPlaceManager().getRequestParameter(FOLDERID);
		int j = 0;
		for(int i = 0; i<listofFoldres; i++) {
			if(FOLDER.equalsIgnoreCase(folderList.get(i).getType())) {
				PlayerDataLogEvents.triggerLibraryViewEvent(folderList.get(i).getParentGooruOid());
				LibraryUnitMenuView libraryUnitMenuView = new LibraryUnitMenuView(folderList.get(i));
				libraryView.getLeftNav().add(libraryUnitMenuView);
				if(j==0&&folderId==null) {
					j++;
					loadingPanel(true);
					libraryUnitMenuView.addStyleName(libraryStyleUc.unitLiActive());
					unitListId = folderList.get(i).getGooruOid();
					libraryView.getFolderTopicTitleLbl().setText(folderList.get(i).getTitle());
					libraryView.getListAllBtn().addClickHandler(new ListAllBtnClickHandler(folderList.get(i).getGooruOid()));
					setTopicListData(folderList.get(i).getFolderItems(), unitListId,folderList.get(i).getParentGooruOid());
				}
			}
		}
		
		final Iterator<Widget> widgets = libraryView.getLeftNav().iterator();
		int widgetCount = 0;
		/**
		 * Iterates the unit wdigets and providing click events for each unit widgets.
		 */
		while (widgets.hasNext()) {
			final Widget widget = widgets.next();
			final LibraryUnitMenuView libraryUnitMenuView = ((LibraryUnitMenuView) widget);
			final int finalWidgetCount = widgetCount;
			libraryUnitMenuView.getUnitMenuItemPanel().addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					long unticlickStarttime = System.currentTimeMillis();
					libraryView.getContentScroll().setVisible(false);
					loadingPanel(true);
					final Iterator<Widget> widgetsPanel = libraryView.getLeftNav().iterator();
					while (widgetsPanel.hasNext()) {
						widgetsPanel.next().removeStyleName(libraryStyleUc.unitLiActive());
					}
					widget.addStyleName(libraryStyleUc.unitLiActive());
					unitListId = libraryUnitMenuView.getUnitId();
					libraryView.getFolderTopicTitleLbl().setText(libraryUnitMenuView.getTitle());
					libraryView.getListAllBtn().addClickHandler(new ListAllBtnClickHandler(libraryUnitMenuView.getUnitId()));
					if(finalWidgetCount==0) {
						setTopicListData(folderList.get(finalWidgetCount).getFolderItems(), unitListId,libraryUnitMenuView.getLibraryGooruOid());
					} else {
						getUiHandlers().getPartnerChildFolderItems(unitListId, 1,libraryUnitMenuView.getLibraryGooruOid());
					}
				}
			});
			widgetCount++;
		}
	}

	/**
	 * Creates each units topic widgets.
	 */
	@Override
	public void setTopicListData(ArrayList<PartnerFolderDo> folderListDo, String folderId,String libraryGooruOid) {
		long startTime = System.currentTimeMillis();
		libraryView.getContentScroll().clear();
		try {
			int count = 0; 
			for(int i = 0; i <folderListDo.size(); i++) {
				count++;
				libraryView.getContentScroll().add(new LibraryTopicListView(folderListDo.get(i), count, AppClientFactory.getCurrentPlaceToken(),libraryGooruOid));
			}
			libraryView.getContentScroll().setVisible(true);
			loadingPanel(false);
		} catch (Exception e) {
			loadingPanel(false);
			AppClientFactory.printSevereLogger(e.toString());
		}
	}
	
	/**
	 * Calls the method to get the respective library banner image.
	 */
	private void setCourseImageData() {
		libraryView.setCourseData(getPartnerName());
	}
	
	/**
	 * Sets the respective library banner image.
	 * @return
	 */
	private CourseDo getPartnerName() {
		String partnerPlace = AppClientFactory.getCurrentPlaceToken();
		CourseDo courseDo = new CourseDo();
		ThumbnailDo thumbnailDo = new ThumbnailDo();
		LibraryUserDo libraryUserDo = new LibraryUserDo();

		Map<String, LoadBannerActionInterface.setLibraryBanner> libraryVal = new HashMap<String,LoadBannerActionInterface.setLibraryBanner>();
		libraryVal = addAllPartnerLibraries();
		
		LoadBannerActionInterface.setLibraryBanner actionInterface = libraryVal.get(partnerPlace); 

		actionInterface.loadLibBanner(courseDo, thumbnailDo, libraryUserDo);
		courseDo = actionInterface.getUpdatedCourseDo();
		return courseDo;
	}
	
	/**
	 * Loads all the libraries into a hash map.
	 * @return {@link Map}
	 */
	public static Map<String, LoadBannerActionInterface.setLibraryBanner> addAllPartnerLibraries() {

		Map<String, LoadBannerActionInterface.setLibraryBanner> map = new HashMap<String,LoadBannerActionInterface.setLibraryBanner>();

		map.put(PlaceTokens.AUTODESK,AutodeskBanner.getAutodeskInstance());
		map.put(PlaceTokens.ONR,ONRBanner.getOnrInstance());
		map.put(PlaceTokens.NGC,NgcBanner.getNgcInstance());
		map.put(PlaceTokens.FTE,FteBanner.getFteInstance());
		map.put(PlaceTokens.WSPWH,WspwhBanner.getWsphInstance());
		map.put(PlaceTokens.LESSONOPOLY,LessonopolyBanner.getLessonpolyInstance());
		map.put(PlaceTokens.FINCAPINC,FincapIncBanner.getfincapInstance());
		map.put(PlaceTokens.PSDPAL,PsdlBanner.getPsdpalIstance());
		map.put(PlaceTokens.YOUTHVOICES, YouthVoicesBanner.getYouthVoicesIstance());
		map.put(PlaceTokens.GEOEDUCATION, GeoEduBanner.getGeoEduIstance());
		map.put(PlaceTokens.LPS, LpsBanner.getLpsIstance());
		map.put(PlaceTokens.CORE_LIBRARY, CoreBanner.getcoreIstance());
		map.put(PlaceTokens.YESD_LIBRARY, YesdBanner.getyesdIstance());
		map.put(PlaceTokens.ESYP, EsypBanner.getEsypIstance());
		map.put(PlaceTokens.CCST_Cal_TAC, CcstBanner.getCcstIstance());
		map.put(PlaceTokens.TICAL, TicalBanner.getTicalInstance());
		map.put(PlaceTokens.ASPIRE_EPACS, AspireBanner.getAspireInstance());

		return map;

	}

	
	/**
	 * This method will display the loading image.
	 */

	@Override
	public void loadingPanel(boolean isVisible) {
		libraryView.getContainer().getElement().getStyle().setMarginTop(-50, Unit.PX);
		libraryView.getCourseTabs().setVisible(false);
		libraryView.getLoadingIconPanel().setVisible(isVisible);
		libraryView.getFolderListPanel().setVisible(!isVisible);
	}
	
	@Override
	public void getComingSoonText(boolean isVisible) {
		libraryView.getComingSoonLabel().setVisible(isVisible);
	}

	@Override
	public void clearPanels() {
		libraryView.getContentScroll().clear();
		libraryView.getLeftNav().clear();
	}
	
	/**
	 * Inner class for implementing autodesk library banner.
	 * 
	 *
	 */
	private static class AutodeskBanner implements LoadBannerActionInterface.setLibraryBanner{
		
		private static AutodeskBanner autodeskBannerInstance;
		private CourseDo libCourseDo;
		
	    // Singleton prevents any other class from instantiating
	    private AutodeskBanner() {
	    }
	 
	    // Providing Global point of access
	    public static AutodeskBanner getAutodeskInstance() {
	        if (null == autodeskBannerInstance) { 
	        	autodeskBannerInstance = new AutodeskBanner();
	        }
	        return autodeskBannerInstance;
	    }
		
		@Override
		public void loadLibBanner(CourseDo courseDo, ThumbnailDo thumbnailDo,LibraryUserDo libraryUserDo) {
			courseDo.setLabel(i18n.GL2027());
			thumbnailDo.setUrl(Constants.AUTODESK_BANNER);
			libraryUserDo.setPartnerName(i18n.GL1566());
			libraryUserDo.setPartnerUrl(i18n.GL1567());
			libCourseDo = setCourseDoObject(courseDo,thumbnailDo,libraryUserDo);
		}

		@Override
		public CourseDo getUpdatedCourseDo() {
			return libCourseDo;
		}
	}
	

	/**
	 * Inner class for implementing library banner.
	 * 
	 *
	 */
	private static class ONRBanner implements LoadBannerActionInterface.setLibraryBanner{

		
		private static ONRBanner ONRBannerInstance;
		private CourseDo libCourseDo;
		 
	    // Singleton prevents any other class from instantiating
	    private ONRBanner() {
	    }
	 
	    // Providing Global point of access
	    public static ONRBanner getOnrInstance() {
	        if (null == ONRBannerInstance) { 
	        	ONRBannerInstance = new ONRBanner();
	        }
	        return ONRBannerInstance;
	    }

		@Override
		public void loadLibBanner(CourseDo courseDo, ThumbnailDo thumbnailDo,LibraryUserDo libraryUserDo) {
			courseDo.setLabel(i18n.GL2028());
			thumbnailDo.setUrl(Constants.ONR_BANNER);
			libraryUserDo.setPartnerName(i18n.GL1568());
			libraryUserDo.setPartnerUrl(i18n.GL1569());	
			libCourseDo = setCourseDoObject(courseDo,thumbnailDo,libraryUserDo);
		}

		@Override
		public CourseDo getUpdatedCourseDo() {
			return libCourseDo;
		}
	}
	

	
	/**
	 * Inner class for implementing NgC library banner.
	 * 
	 *
	 */
	private static class NgcBanner implements LoadBannerActionInterface.setLibraryBanner{

		
		private static NgcBanner ngcBannerInstance;
		private CourseDo libCourseDo;
		 
	    // Singleton prevents any other class from instantiating
	    private NgcBanner() {
	    }
	 
	    // Providing Global point of access
	    public static NgcBanner getNgcInstance() {
	        if (null == ngcBannerInstance) { 
	        	ngcBannerInstance = new NgcBanner();
	        }
	        return ngcBannerInstance;
	    }

		@Override
		public void loadLibBanner(CourseDo courseDo, ThumbnailDo thumbnailDo,LibraryUserDo libraryUserDo) {
			courseDo.setLabel(i18n.GL2030());
			thumbnailDo.setUrl(Constants.NGC_BANNER);
			libraryUserDo.setPartnerName(i18n.GL1572());
			libraryUserDo.setPartnerUrl(i18n.GL1573());
			libCourseDo = setCourseDoObject(courseDo,thumbnailDo,libraryUserDo);
		}

		@Override
		public CourseDo getUpdatedCourseDo() {
			return libCourseDo;
		}
	}
	
	/**
	 * Inner class for implementing FTE library banner.
	 * 
	 *
	 */
	private static class FteBanner implements LoadBannerActionInterface.setLibraryBanner{

		private static FteBanner fteBannerInstance;
		private CourseDo libCourseDo;
		 
	    // Singleton prevents any other class from instantiating
	    private FteBanner() {
	    }
	 
	    // Providing Global point of access
	    public static FteBanner getFteInstance() {
	        if (null == fteBannerInstance) { 
	        	fteBannerInstance = new FteBanner();
	        }
	        return fteBannerInstance;
	    }


		
		@Override
		public void loadLibBanner(CourseDo courseDo, ThumbnailDo thumbnailDo,LibraryUserDo libraryUserDo) {
			courseDo.setLabel(i18n.GL2029());
			thumbnailDo.setUrl(Constants.FTE_BANNER);
			libraryUserDo.setPartnerName(i18n.GL1570());
			libraryUserDo.setPartnerUrl(i18n.GL1571());
			libCourseDo = setCourseDoObject(courseDo,thumbnailDo,libraryUserDo);
		}

		@Override
		public CourseDo getUpdatedCourseDo() {
			return libCourseDo;
		}
	}
	

	/**
	 * Inner class for implementing WSPH library banner.
	 * 
	 */
	private static class WspwhBanner implements LoadBannerActionInterface.setLibraryBanner{


		private static WspwhBanner wsphBannerInstance;
		private CourseDo libCourseDo;
		
		 
	    // Singleton prevents any other class from instantiating
	    private WspwhBanner() {
	    }
	 
	    // Providing Global point of access
	    public static WspwhBanner getWsphInstance() {
	        if (null == wsphBannerInstance) { 
	        	wsphBannerInstance = new WspwhBanner();
	        }
	        return wsphBannerInstance;
	    }
		
		@Override
		public void loadLibBanner(CourseDo courseDo, ThumbnailDo thumbnailDo,LibraryUserDo libraryUserDo) {
			courseDo.setLabel(i18n.GL2031());
			thumbnailDo.setUrl(Constants.WSPH_BANNER);
			libraryUserDo.setPartnerName(i18n.GL1574());
			libraryUserDo.setPartnerUrl(i18n.GL1575());
			libCourseDo = setCourseDoObject(courseDo,thumbnailDo,libraryUserDo);
		}

		@Override
		public CourseDo getUpdatedCourseDo() {
			return libCourseDo;
		}
	}
	
	
	
	/**
	 * Inner class for implementing Lessonopoly library banner.
	 *
	 */
	private static class LessonopolyBanner implements LoadBannerActionInterface.setLibraryBanner{

		private static LessonopolyBanner lessonopolyBannerInstance;
		private CourseDo libCourseDo;
		
	    // Singleton prevents any other class from instantiating
	    private LessonopolyBanner() {
	    }
	 
	    // Providing Global point of access
	    public static LessonopolyBanner getLessonpolyInstance() {
	        if (null == lessonopolyBannerInstance) { 
	        	lessonopolyBannerInstance = new LessonopolyBanner();
	        }
	        return lessonopolyBannerInstance;
	    }
		
		@Override
		public void loadLibBanner(CourseDo courseDo, ThumbnailDo thumbnailDo,LibraryUserDo libraryUserDo) {
			courseDo.setLabel(i18n.GL2032());
			thumbnailDo.setUrl(Constants.LESSONOPOLY_BANNER);
			libraryUserDo.setPartnerName(i18n.GL1576());
			libraryUserDo.setPartnerUrl(i18n.GL1577());
			libCourseDo = setCourseDoObject(courseDo,thumbnailDo,libraryUserDo);
		}

		@Override
		public CourseDo getUpdatedCourseDo() {
			return libCourseDo;
		}
	}
	

	/**
	 * Inner class for implementing Fincap library banner.
	 * 
	 */
	private static class FincapIncBanner implements LoadBannerActionInterface.setLibraryBanner{

		private static FincapIncBanner fincapIncBannerInstance;
		private CourseDo libCourseDo;
		
	    // Singleton prevents any other class from instantiating
	    private FincapIncBanner() {
	    }
	 
	    // Providing Global point of access
	    public static FincapIncBanner getfincapInstance() {
	        if (null == fincapIncBannerInstance) { 
	        	fincapIncBannerInstance = new FincapIncBanner();
	        }
	        return fincapIncBannerInstance;
	    }
		
		@Override
		public void loadLibBanner(CourseDo courseDo, ThumbnailDo thumbnailDo,LibraryUserDo libraryUserDo) {
			courseDo.setLabel(i18n.GL2033());
			thumbnailDo.setUrl(Constants.FINCAP_BANNER);
			libraryUserDo.setPartnerName(i18n.GL1765());
			libraryUserDo.setPartnerUrl(i18n.GL1766());
			libCourseDo = setCourseDoObject(courseDo,thumbnailDo,libraryUserDo);
		}

		@Override
		public CourseDo getUpdatedCourseDo() {
			return libCourseDo;
		}
	}
	

	/**
	 * Inner class for implementing PSDL library banner.
	 * 
	 */
	private static class PsdlBanner implements LoadBannerActionInterface.setLibraryBanner{


		private static PsdlBanner psdlBannerInstance;
		private CourseDo libCourseDo;
		
		// Singleton prevents any other class from instantiating
		private PsdlBanner() {
		}

		// Providing Global point of access
		public static PsdlBanner getPsdpalIstance() {
			if (null == psdlBannerInstance) { 
				psdlBannerInstance = new PsdlBanner();
			}
			return psdlBannerInstance;
		}

		@Override
		public void loadLibBanner(CourseDo courseDo, ThumbnailDo thumbnailDo,LibraryUserDo libraryUserDo) {
			courseDo.setLabel(i18n.GL2034());
			thumbnailDo.setUrl(Constants.PSDPAL_BANNER);
			libraryUserDo.setPartnerName(i18n.GL1763());
			libraryUserDo.setPartnerUrl(i18n.GL1764());
			libCourseDo = setCourseDoObject(courseDo,thumbnailDo,libraryUserDo);
		}
		

		@Override
		public CourseDo getUpdatedCourseDo() {
			return libCourseDo;
		}
	}
	
	/**
	 * Inner class for implementing Youthvoice library banner.
	 * 
	 */
	private static class YouthVoicesBanner implements LoadBannerActionInterface.setLibraryBanner{

		private static YouthVoicesBanner youthVoiceBannerInstance;
		private CourseDo libCourseDo;
		
		// Singleton prevents any other class from instantiating
		private YouthVoicesBanner() {
		}

		// Providing Global point of access
		public static YouthVoicesBanner getYouthVoicesIstance() {
			if (null == youthVoiceBannerInstance) { 
				youthVoiceBannerInstance = new YouthVoicesBanner();
			}
			return youthVoiceBannerInstance;
		}


		@Override
		public void loadLibBanner(CourseDo courseDo, ThumbnailDo thumbnailDo,LibraryUserDo libraryUserDo) {
			courseDo.setLabel(i18n.GL2040());
			thumbnailDo.setUrl(Constants.YOUTH_VOICES_BANNER);
			libraryUserDo.setPartnerName(i18n.GL2042());
			libraryUserDo.setPartnerUrl(i18n.GL2043());
			libCourseDo = setCourseDoObject(courseDo,thumbnailDo,libraryUserDo);
		}

		@Override
		public CourseDo getUpdatedCourseDo() {
			return libCourseDo;
		}
	}

	

	/**
	 * @return the placeToken
	 */
	public String getPlaceToken() {
		return placeToken;
	}

	/**
	 * @param placeToken the placeToken to set
	 */
	public void setPlaceToken(String placeToken) {
		this.placeToken = placeToken;
	}


	/**
	 * Inner class for implementing GeoEdu library banner.
	 * 
	 */
	private static class GeoEduBanner implements LoadBannerActionInterface.setLibraryBanner{


		private static GeoEduBanner geoEduBannerInstance;
		private CourseDo libCourseDo;
		
		// Singleton prevents any other class from instantiating
		private GeoEduBanner() {
		}

		// Providing Global point of access
		public static GeoEduBanner getGeoEduIstance() {
			if (null == geoEduBannerInstance) { 
				geoEduBannerInstance = new GeoEduBanner();
			}
			return geoEduBannerInstance;
		}

		@Override
		public void loadLibBanner(CourseDo courseDo, ThumbnailDo thumbnailDo,LibraryUserDo libraryUserDo) {
			courseDo.setLabel(i18n.GL2041());
			thumbnailDo.setUrl(Constants.GEOEDU_BANNER);
			libraryUserDo.setPartnerName(i18n.GL2044());
			libraryUserDo.setPartnerUrl(i18n.GL2045());
			libCourseDo = setCourseDoObject(courseDo,thumbnailDo,libraryUserDo);
		}

		@Override
		public CourseDo getUpdatedCourseDo() {
			return libCourseDo;
		}
	}
	

	/**
	 * Inner class for implementing LPS library banner.
	 * 
	 */
	private static class LpsBanner implements LoadBannerActionInterface.setLibraryBanner{


		private static LpsBanner lpsBannerInstance;
		private CourseDo libCourseDo;
		
		// Singleton prevents any other class from instantiating
		private LpsBanner() {
		}

		// Providing Global point of access
		public static LpsBanner getLpsIstance() {
			if (null == lpsBannerInstance) { 
				lpsBannerInstance = new LpsBanner();
			}
			return lpsBannerInstance;
		}

		@Override
		public void loadLibBanner(CourseDo courseDo, ThumbnailDo thumbnailDo,LibraryUserDo libraryUserDo) {
			courseDo.setLabel(i18n.GL2053());
			thumbnailDo.setUrl(Constants.LPS_BANNER);
			libraryUserDo.setPartnerName(i18n.GL2065());
			libraryUserDo.setPartnerUrl(i18n.GL2066());
			libCourseDo = setCourseDoObject(courseDo,thumbnailDo,libraryUserDo);
		}

		@Override
		public CourseDo getUpdatedCourseDo() {
			return libCourseDo;
		}
	}
	
	/**
	 * Inner class for implementing CORE library banner.
	 * 
	 */
	private static class CoreBanner implements LoadBannerActionInterface.setLibraryBanner{

		private static CoreBanner coreBannerInstance;
		private CourseDo libCourseDo;
		
		// Singleton prevents any other class from instantiating
		private CoreBanner() {
		}

		// Providing Global point of access
		public static CoreBanner getcoreIstance() {
			if (null == coreBannerInstance) { 
				coreBannerInstance = new CoreBanner();
			}
			return coreBannerInstance;
		}

		@Override
		public void loadLibBanner(CourseDo courseDo, ThumbnailDo thumbnailDo,LibraryUserDo libraryUserDo) {
			courseDo.setLabel(i18n.GL2108());
			thumbnailDo.setUrl(Constants.CORE_BANNER);
			libraryUserDo.setPartnerName(i18n.GL2110());
			libraryUserDo.setPartnerUrl(i18n.GL2111());
			libCourseDo = setCourseDoObject(courseDo,thumbnailDo,libraryUserDo);
		}

		@Override
		public CourseDo getUpdatedCourseDo() {
			return libCourseDo;
		}
	}

	
	/**
	 * Inner class for implementing YESD library banner.
	 * 
	 */
	private static class YesdBanner implements LoadBannerActionInterface.setLibraryBanner{

		private static YesdBanner yesdBannerInstance;
		private CourseDo libCourseDo;
		
		// Singleton prevents any other class from instantiating
		private YesdBanner() {
		}

		// Providing Global point of access
		public static YesdBanner getyesdIstance() {
			if (null == yesdBannerInstance) { 
				yesdBannerInstance = new YesdBanner();
			}
			return yesdBannerInstance;
		}

		@Override
		public void loadLibBanner(CourseDo courseDo, ThumbnailDo thumbnailDo,LibraryUserDo libraryUserDo) {
			courseDo.setLabel("Yuma Elementary School District ONE");
			thumbnailDo.setUrl(Constants.YESD_BANNER);
			libraryUserDo.setPartnerName(" About Yuma Elementary School District ONE");
			libraryUserDo.setPartnerUrl("http://www.yuma.org/");
			libCourseDo = setCourseDoObject(courseDo,thumbnailDo,libraryUserDo);
		}

		@Override
		public CourseDo getUpdatedCourseDo() {
			return libCourseDo;
		}
	}

	
	

	/**
	 * Inner class for implementing ESYP library banner.
	 * 
	 */
	private static class EsypBanner implements LoadBannerActionInterface.setLibraryBanner{

		private static EsypBanner esypBannerInstance;
		private CourseDo libCourseDo;
		
		// Singleton prevents any other class from instantiating
		private EsypBanner() {
		}

		// Providing Global point of access
		public static EsypBanner getEsypIstance() {
			if (null == esypBannerInstance) { 
				esypBannerInstance = new EsypBanner();
			}
			return esypBannerInstance;
		}


		@Override
		public void loadLibBanner(CourseDo courseDo, ThumbnailDo thumbnailDo,LibraryUserDo libraryUserDo) {
			courseDo.setLabel(i18n.GL2174());
			thumbnailDo.setUrl(Constants.ESYP_BANNER);
			libraryUserDo.setPartnerName(i18n.GL2175());
			libraryUserDo.setPartnerUrl(i18n.GL2176());
			libCourseDo = setCourseDoObject(courseDo,thumbnailDo,libraryUserDo);
		}

		@Override
		public CourseDo getUpdatedCourseDo() {
			return libCourseDo;
		}
	}

	/**
	 * Inner class for implementing CCST library banner.
	 * 
	 */
	private static class CcstBanner implements LoadBannerActionInterface.setLibraryBanner{


		private static CcstBanner ccstBannerInstance;
		private CourseDo libCourseDo;
		
		// Singleton prevents any other class from instantiating
		private CcstBanner() {
		}

		// Providing Global point of access
		public static CcstBanner getCcstIstance() {
			if (null == ccstBannerInstance) { 
				ccstBannerInstance = new CcstBanner();
			}
			return ccstBannerInstance;
		}

		@Override
		public void loadLibBanner(CourseDo courseDo, ThumbnailDo thumbnailDo,LibraryUserDo libraryUserDo) {
			courseDo.setLabel(i18n.GL2179());
			thumbnailDo.setUrl(Constants.CCST_BANNER);
			libraryUserDo.setPartnerName(i18n.GL2177());
			libraryUserDo.setPartnerUrl(i18n.GL2178());
			libCourseDo = setCourseDoObject(courseDo,thumbnailDo,libraryUserDo);
		}

		@Override
		public CourseDo getUpdatedCourseDo() {
			return libCourseDo;
		}
	}


	/**
	 * Inner class for implementing TICAl library banner.
	 L
	 */
	private static class TicalBanner implements LoadBannerActionInterface.setLibraryBanner{

		private static TicalBanner ticalBannerInstance;
		private CourseDo libCourseDo;
		
		// Singleton prevents any other class from instantiating
		private TicalBanner() {
		}

		// Providing Global point of access
		public static TicalBanner getTicalInstance() {
			if (null == ticalBannerInstance) { 
				ticalBannerInstance = new TicalBanner();
			}
			return ticalBannerInstance;
		}

		@Override
		public void loadLibBanner(CourseDo courseDo, ThumbnailDo thumbnailDo,LibraryUserDo libraryUserDo) {
			courseDo.setLabel(i18n.GL2186());
			thumbnailDo.setUrl(Constants.TICAL_BANNER);
			libraryUserDo.setPartnerName(i18n.GL2187());
			libraryUserDo.setPartnerUrl(i18n.GL2188());
			libCourseDo = setCourseDoObject(courseDo,thumbnailDo,libraryUserDo);
		}

		@Override
		public CourseDo getUpdatedCourseDo() {
			return libCourseDo;
		}
	}

	
	/**
	 * Inner class for implementing Aspire library banner.
	 * 
	 */
	private static class AspireBanner implements LoadBannerActionInterface.setLibraryBanner{


		private static AspireBanner aspireBannerInstance;
		
		private CourseDo libCourseDo;
		
		// Singleton prevents any other class from instantiating
		private AspireBanner() {
		}

		// Providing Global point of access
		public static AspireBanner getAspireInstance() {
			if (null == aspireBannerInstance) { 
				aspireBannerInstance = new AspireBanner();
			}
			return aspireBannerInstance;
		}

		@Override
		public void loadLibBanner(CourseDo courseDo, ThumbnailDo thumbnailDo,LibraryUserDo libraryUserDo) {
			courseDo.setLabel(i18n.GL3107());  
			thumbnailDo.setUrl(Constants.ASPIRE_BANNER);
			libraryUserDo.setPartnerName(i18n.GL3108());
			libraryUserDo.setPartnerUrl(i18n.GL3109());
			libCourseDo = setCourseDoObject(courseDo,thumbnailDo,libraryUserDo);
		}
		
		@Override
		public CourseDo getUpdatedCourseDo() {
			return libCourseDo;
		}
	}
	
	/**
	 * Setting the course Do obj
	 * @param courseDo {@link CourseDo}
	 * @param thumbnailDo {@link ThumbnailDo}
	 * @param libraryUserDo {@link LibraryUserDo}
	 * @return courseDo {@link CourseDo}
	 */
	public static CourseDo setCourseDoObject(CourseDo courseDo,ThumbnailDo thumbnailDo,LibraryUserDo libraryUserDo){
		courseDo.setThumbnails(thumbnailDo);
		courseDo.setCreator(libraryUserDo);
		return courseDo;
	}
	
	/**
	 * This Inner class used to navigate to Folder TOC page when click on ListAll button.
	 */
	public class ListAllBtnClickHandler implements ClickHandler{
		String folderId="";
		public ListAllBtnClickHandler(String folderId){
			this.folderId=folderId;
		}
		@Override
		public void onClick(ClickEvent event) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("id", folderId);
			params.put("libName", getPlaceToken());
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.FOLDER_TOC,params);
		}
		
	}
}
