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
package org.ednovo.gooru.client.mvp.play.collection.body;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.Composite;
import com.nitrous.gwt.earth.client.api.GELayerId;
import com.nitrous.gwt.earth.client.api.GEPlugin;
import com.nitrous.gwt.earth.client.api.GEPluginReadyListener;
import com.nitrous.gwt.earth.client.api.GEVisibility;
import com.nitrous.gwt.earth.client.api.GoogleEarth;
import com.nitrous.gwt.earth.client.api.GoogleEarthWidget;
import com.nitrous.gwt.earth.client.api.KmlObject;
import com.nitrous.gwt.earth.client.api.KmlTour;
import com.nitrous.gwt.earth.client.api.event.KmlLoadCallback;

public class GwtEarthWidget extends Composite {
	 private GoogleEarthWidget earth;
	 private String url="";

     static {
             // Expose the walkKmlDom method to the JavaScript world
             registerMethod();
     }
	 public  GwtEarthWidget(String url){
		 this.url=url;


		earth = new GoogleEarthWidget();
		earth.setStyleName("map3dcontainer");
		 initWidget(earth);


		 GoogleEarth.loadApi(new Runnable(){

             @Override
             public void run() {

                     // start the application
                     onApiLoaded();

             }
});
}
	 private void onApiLoaded() {
	  // construct the UI widget
         // register a listener to be notified when the earth plug-in has loaded
         earth.addPluginReadyListener(new GEPluginReadyListener() {
                 public void pluginReady(GEPlugin ge) {
                	// show map content once the plugin has loaded
                      loadMapContent();

                 }

                public void pluginInitFailure() {
                	 // failure!
                        // Window.alert("Failed to initialize Google Earth Plug-in");
                 }
         });

        // RootLayoutPanel.get().add(earth);

         // begin loading the Google Earth Plug-in
         earth.init();
 }
private void loadMapContent() {
     final GEPlugin ge = earth.getGEPlugin();
     ge.getWindow().setVisibility(true);
     ge.getNavigationControl().setVisibility(GEVisibility.VISIBILITY_AUTO);
     ge.enableLayer(GELayerId.LAYER_BORDERS, true);
     ge.enableLayer(GELayerId.LAYER_ROADS, true);
     GoogleEarth.fetchKml(ge, url, new KmlLoadCallback(){
         @Override
         public void onLoaded(KmlObject feature) {
                 // check whether the KML loaded successfully.
                 if (feature == null) {
                        // Window.alert("Failed to load KML");
                         return;
                 }

                 // add the fetched KML into the Earth instance
         ge.getFeatures().appendChild(feature);

         // walk the DOM and find the first Kml tour
         KmlTour tour = null;
         try {
                 tour = findKmlTour(feature);
         } catch (Throwable t) {
                 GWT.log("Failed to find tour", t);
         }

         // if we found a tour then play it
         if (tour != null) {
                         // play the tour
                         ge.getTourPlayer().setTour(tour);
                         ge.getTourPlayer().play();
         } else {
               //  Window.alert("Failed to find tour in KML");
         }
         }
     });

	 }

/**
 * Walk the specified DOM and find the first KmlTour
 * @param root The root node of the KML DOM to search
 * @return The KmlTour that has been found or null
 */
private static final native KmlTour findKmlTour(KmlObject root) /*-{
        var tour;
        $wnd.walkKmlDom(root, function() {
    if (this.getType() == 'KmlTour') {
       tour = this;
       return false;
    }
    return true;
 });
 return tour;
}-*/;

/**
 * Register the walkKmlDom Java function so that it is accessible from the JavaScript world.
 */
private static final native void registerMethod() /*-{
        $wnd.walkKmlDom = $entry(@org.ednovo.gooru.client.mvp.play.collection.body.GwtEarthWidget::walkKmlDom(Lcom/nitrous/gwt/earth/client/api/KmlObject;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;));
}-*/;

/**
 * JavaScript method taken from here: http://earth-api-samples.googlecode.com/svn/trunk/lib/kmldomwalk.js
 *
 * Walks a KML object, calling a given visit function for each object in
 * the KML DOM. The lone argument must be either a visit function or an
 * options literal.
 *
 * NOTE: walking the DOM can have pretty poor performance on very large
 * hierarchies, as first time accesses to KML objects from JavaScript
 * incur some overhead in the API.
 *
 * @param {KmlObject} rootObject The root of the KML object hierarchy to walk.
 * @param {Function} visitCallback The function to call upon visiting
 *     a node in the DOM. The 'this' variable in the callback function will be
 *     bound to the object being visited. The lone argument passed to this
 *     function will be an object literal for the call context. To get the
 *     current application-specific call context, use the 'current' property
 *     of the context object. To set the context for all child calls, set the
 *     'child' property of the context object. To prevent walking the children
 *     of the current object, set the 'walkChildren' property of the context
 *     object to false. To stop the walking process altogether,
 *     return false in the function.
 * @param {Object} [options] The walk options:
 * @param {boolean} [options.features] Descend into feature containers?
 *     Default true.
 * @param {boolean} [options.geometries] Descend into geometry containers?
 *     Default false.
 * @param {Object} [options.rootContext] The application-specific context to
 *     pass to the root item.
 */
private static final native JavaScriptObject walkKmlDom(KmlObject rootObject, JavaScriptObject visitCallback, JavaScriptObject options) /*-{
        options = options || {};

        if (!('features' in options)) {
                options.features = true;
        }

        if (!('geometries' in options)) {
                options.geometries = false;
        }

        var recurse_ = function(object, currentContext) {
                var contextArgument = {
                        current : currentContext,
                        child : currentContext,
                        walkChildren : true
                };

                // walk object
                var retValue = visitCallback.call(object, contextArgument);
                if (!retValue && typeof retValue !== 'undefined') {
                        return false;
                }

                if (!contextArgument.walkChildren) {
                        return true;
                }

                var objectContainer = null; // GESchemaObjectContainer

                // check if object is a parent
                if ('getFeatures' in object) { // GEFeatureContainer
                        if (options.features) {
                                objectContainer = object.getFeatures();
                        }
                } else if ('getGeometry' in object) { // KmlFeature - descend into geoms.
                        if (options.geometries && object.getGeometry()) {
                                recurse_(object.getGeometry(), contextArgument.child);
                        }
                } else if ('getGeometries' in object) { // GEGeometryContainer
                        if (options.geometries) {
                                objectContainer = object.getGeometries();
                        }
                } else if ('getInnerBoundaries' in object) { // GELinearRingContainer
                        if (options.geometries) {
                                objectContainer = object.getInnerBoundaries();
                        }
                }

                // iterate through children if object is a parent and recurse so they
                // can be walked
                if (objectContainer && objectContainer.hasChildNodes()) {
                        var childNodes = objectContainer.getChildNodes();
                        var numChildNodes = childNodes.getLength();

                        for ( var i = 0; i < numChildNodes; i++) {
                                var child = childNodes.item(i);

                                if (!recurse_(child, contextArgument.child))
                                        return false;
                        }
                }

                return true;
        };

        recurse_(rootObject, options.rootContext);
}-*/;

}
