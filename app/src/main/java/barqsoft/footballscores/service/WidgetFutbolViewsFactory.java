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

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.ScoresDBHelper;

/*
  We have to send the
 contents of the app widget’s UI via a RemoteViews, we will need
  to provide the rows or cells via RemoteViews using this class.
 */
public class WidgetFutbolViewsFactory implements RemoteViewsService.RemoteViewsFactory {
  private static final String[] items= { "lorem", "ipsum", "dolor",
      "sit", "amet", "consectetuer", "adipiscing", "elit", "morbi",
      "vel", "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam",
      "vel", "erat", "placerat", "ante", "porttitor", "sodales",
      "pellentesque", "augue", "purus" };
  private Context ctxt=null;
  private int appWidgetId;
  private ScoresDBHelper db;

  public WidgetFutbolViewsFactory(Context ctxt, Intent intent) {
    this.ctxt=ctxt;
    appWidgetId= intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
  }

  @Override
  public void onCreate() {
    // no-op
    db = new ScoresDBHelper(ctxt);
  }

  @Override
  public void onDestroy() {
    // no-op
    if (db!=null){
      db.close();
    }
  }

  @Override
  public int getCount() {
    return(items.length);
  }

  @Override //returns the row or cell View for a given position in your data set
  public RemoteViews getViewAt(int position) {
    RemoteViews row= new RemoteViews(ctxt.getPackageName(), R.layout.row);

    row.setImageViewResource(R.id.home_image_crest, R.drawable.ic_launcher);
    //rawQuery("Column Index"
    //db.getReadableDatabase().query()
    //row.setTextViewText(R.id.home_text_name, db.getReadableDatabase().query(DatabaseContract.SCORES_TABLE, projection,SCORES_BY_DATE,selectionArgs,null,null,sortOrder);
    row.setTextViewText(R.id.score_text_widget,"Score");
    row.setTextViewText(R.id.away_text_name, "Away");
    row.setImageViewResource(R.id.away_image_crest,R.drawable.ic_launcher);

   // mHolder.home_name.setText(cursor.getString(COL_HOME));

    /*
    The contents of this "fill-in" Intent are merged intot he "template" PendingIntent from
    setPendingIntentTemplate(), and the resulting PendingIntent is what is invoked when the user
    taps on an item in the AdapterView.
     */
    Intent i=new Intent();
    Bundle extras=new Bundle();
    extras.putString(WidgetProvider.EXTRA_WORD, items[position]);
    extras.putInt(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
    i.putExtras(extras);
    row.setOnClickFillInIntent(android.R.id.text1, i);

    return(row);
  }

  @Override
  public RemoteViews getLoadingView() {
    return(null); // if null is returned Android will use the default placeholder
  }

  @Override
  public int getViewTypeCount() {
    return(1);
  }

  @Override
  public long getItemId(int position) {
    return(position);
  }

  @Override
  public boolean hasStableIds() {
    return(true);
  }

  @Override
  public void onDataSetChanged() {
    // no-op
  }
}