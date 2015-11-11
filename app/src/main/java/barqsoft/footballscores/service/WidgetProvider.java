/***
  Copyright (c) 2008-2012 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  From _The Busy Coder's Guide to Android Development_
    http://commonsware.com/Android
*/

package barqsoft.footballscores.service;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;

public class WidgetProvider extends AppWidgetProvider {
  public static String EXTRA_WORD= "com.commonsware.android.appwidget.lorem.WORD";

  @Override
  public void onUpdate(Context ctxt, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

    for (int i=0; i<appWidgetIds.length; i++) {
      Intent svcIntent=new Intent(ctxt, WidgetService.class);
      //begin packaging extras on the Intent to deliver
      // to the RemoteViewsService when it is invoked by the app widget host.
      svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
      svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

      //Making our RemoteView to pass
      RemoteViews widget=new RemoteViews(ctxt.getPackageName(), R.layout.widget);
      //Setting a RemoteAdapter to the RemoteView being created and passed to our RemoteViewService
      widget.setRemoteAdapter(R.id.list_view_widget, svcIntent);

      Intent clickIntent=new Intent(ctxt, MainActivity.class);
      /*
      The call to setPendingIntentTemplate() is
      where we provide a PendingIntent that will be used
      as the template for all row or cell clicks. As we will
      see in a bit, the underlying Intent in the PendingIntent
      will have more data added to it by our RemoteViewsFactory
       */
      PendingIntent clickPI= PendingIntent.getActivity(ctxt, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
      widget.setPendingIntentTemplate(R.id.list_view_widget, clickPI);

      appWidgetManager.updateAppWidget(appWidgetIds[i], widget);
    }
    
    super.onUpdate(ctxt, appWidgetManager, appWidgetIds);
  }
}