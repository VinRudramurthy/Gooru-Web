package org.ednovo.gooru.client.mvp.search.util;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;

import com.google.gwt.core.client.GWT;

public class LibraryImagesMap {
	   private static LibraryImagesMap libraryNames =null;
	   static Map<String, List<String>> mapObj=new LinkedHashMap<String, List<String>>();
	   private static MessageProperties i18n = GWT.create(MessageProperties.class);
	   private LibraryImagesMap(){
		   mapObj.put(PlaceTokens.SAUSD_LIBRARY, Arrays.asList(i18n.GL3239(), PlaceTokens.SAUSD_LIBRARY,"sausdLogo"));
		   mapObj.put(PlaceTokens.ASPIRE_EPACS, Arrays.asList(i18n.GL3244(),PlaceTokens.ASPIRE_EPACS,"epacsLogo"));
		   	mapObj.put(PlaceTokens.RUSD_LIBRARY, Arrays.asList(i18n.GL3238(),PlaceTokens.RUSD_LIBRARY,"rusdLogo"));
//		 	mapObj.put(PlaceTokens.RUSD_LIBRARY, Arrays.asList("",PlaceTokens.EPISD_LIBRARY,"episdLogo"));
//		   	mapObj.put(PlaceTokens.YCGL_LIBRARY, Arrays.asList(i18n.GL3290_1(),PlaceTokens.YCGL_LIBRARY,"ycglLogo"));
		   	mapObj.put(PlaceTokens.FINCAPINC, Arrays.asList(i18n.GL3250(),PlaceTokens.FINCAPINC,"cfciLogo"));		   	
		   	mapObj.put(PlaceTokens.VALVERDE, Arrays.asList(i18n.GL3241(), PlaceTokens.VALVERDE,"valverdeLogo"));
		   	mapObj.put(PlaceTokens.FTE, Arrays.asList(i18n.GL3246(),PlaceTokens.FTE,"fteLogo"));
		   	mapObj.put(PlaceTokens.CCST_Cal_TAC, Arrays.asList(i18n.GL3243(),PlaceTokens.CCST_Cal_TAC,"ccstLogo"));		   	
		   	mapObj.put(PlaceTokens.GEOEDUCATION, Arrays.asList(i18n.GL3248(),PlaceTokens.GEOEDUCATION,"natgeoLogo"));
		   	
		   	mapObj.put(PlaceTokens.NGC, Arrays.asList(i18n.GL3249(),PlaceTokens.NGC,"ngcLogo"));	   	
		   	mapObj.put(PlaceTokens.CORE_LIBRARY, Arrays.asList(i18n.GL3235(),PlaceTokens.CORE_LIBRARY,"coreLogo"));
		   	
//		   	mapObj.put(PlaceTokens.YESD_LIBRARY, Arrays.asList("YESD1",PlaceTokens.YESD_LIBRARY,""));
		   	
			mapObj.put(PlaceTokens.LPS, Arrays.asList(i18n.GL3237(), PlaceTokens.LPS,"lpsLogo"));
			mapObj.put(PlaceTokens.LUSD, Arrays.asList(i18n.GL3236(), PlaceTokens.LUSD,"lusdLogo"));			
			mapObj.put(PlaceTokens.SUSD, Arrays.asList(i18n.GL3240(), PlaceTokens.SUSD,"susdLogo"));		 
			mapObj.put(PlaceTokens.AUTODESK, Arrays.asList(i18n.GL3242(),PlaceTokens.AUTODESK,"autodeskLogo"));
			mapObj.put(PlaceTokens.ESYP, Arrays.asList(i18n.GL3245(),PlaceTokens.ESYP,"esypLogo"));			
			mapObj.put(PlaceTokens.LESSONOPOLY, Arrays.asList(i18n.GL3253(),PlaceTokens.LESSONOPOLY,"lessonopolyLogo"));
			mapObj.put(PlaceTokens.LIFEBOARD, Arrays.asList(i18n.GL3247(),PlaceTokens.LIFEBOARD,"lifeboardLogo"));	
			mapObj.put(PlaceTokens.ONR, Arrays.asList(i18n.GL3251(),PlaceTokens.ONR,"onrLogo"));
			mapObj.put(PlaceTokens.PSDPAL, Arrays.asList(i18n.GL3252(),PlaceTokens.PSDPAL,"psdLogo"));
			mapObj.put(PlaceTokens.TICAL, Arrays.asList(i18n.GL3254(),PlaceTokens.TICAL,"ticalLogo"));
			mapObj.put(PlaceTokens.WSPWH, Arrays.asList(i18n.GL3255(),PlaceTokens.WSPWH,"wspwhLogo"));
			mapObj.put(PlaceTokens.YOUTHVOICES, Arrays.asList(i18n.GL3256(),PlaceTokens.YOUTHVOICES,"youthvoicesLogo"));
	   }
	   public static LibraryImagesMap getInstance( ) {
		   if(libraryNames == null) {
			   libraryNames = new LibraryImagesMap();
		    }
		    return libraryNames;
	   }
	   protected Map<String, List<String>> getLibraryMap() {
		  return mapObj;
	   }
}
