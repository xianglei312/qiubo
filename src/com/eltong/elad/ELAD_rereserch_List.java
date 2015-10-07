package com.eltong.elad;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.RTalk.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Xml;
import android.widget.ListView;
import android.widget.Toast;

public class ELAD_rereserch_List extends Activity {
	
	private ListView tabcontent_point_lin_research = null;
	private List<MapListImageAndTextRSre> listresearch = null;
	MapListImageAndTextListAdapterRSre saresearch =null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_rereserch_list);
		tabcontent_point_lin_research = (ListView) findViewById(R.id.tabcontent_point_lin_researcha);
		getResearchHistoryList();
	}
	
	private void getResearchHistoryList() {
		new Thread() {
			public void run() {
				StringBuffer strParam = new StringBuffer();
				strParam.append("member_IDX=");
				strParam.append(BasicProperties.MEMBER_ID);
				InputStream is = AppliedMethod.doPostSubmit(
						BasicProperties.HTTP_URL_GetResearchRecordHistory,
						strParam.toString());
				doReadResearchHistoryListXML(is);

				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		}.start();
	}

	public void doReadResearchHistoryListXML(InputStream is) {
		if (is == null) {
			Message msg = new Message();
			msg.what = 0;
			msg.obj = getString(R.string.toast_msg_request_error);
			handler.sendMessage(msg);
		} else {
			try {
				MapListImageAndTextRSre mapresearch = null;
				XmlPullParser xpp = Xml.newPullParser();
				xpp.setInput(is, BasicProperties.CODED_FORMAT);
				int eventCode = xpp.getEventType();
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					String nodeName = xpp.getName();
					switch (eventCode) {
					case XmlPullParser.START_DOCUMENT:
						listresearch = new ArrayList<MapListImageAndTextRSre>();
						break;
					case XmlPullParser.START_TAG:
						if (BasicProperties.XML_TAG5.equals(nodeName)) {
							mapresearch = new MapListImageAndTextRSre();
						} else if (BasicProperties.XML_TAG133.equals(nodeName)) {
							mapresearch.setamount(getString(R.string.buy_depiont)
													+ " "
													+ xpp.nextText()
													+ " "
													+ getString(R.string.buy_fen));
						} else if (BasicProperties.XML_TAG134.equals(nodeName)) {
							mapresearch.setmember_quota(getString(R.string.buy_gongpiont)
													+ " "
													+ xpp.nextText()
													+ " "
													+ getString(R.string.buy_fen));
						} else if (BasicProperties.XML_TAG135.equals(nodeName)) {
							mapresearch.settitle(xpp.nextText());
						} else if (BasicProperties.XML_TAG136.equals(nodeName)) {
							mapresearch.setimages(xpp.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						if (BasicProperties.XML_TAG5.equals(nodeName)) {
							listresearch.add(mapresearch);
						}
						break;
					}
					eventCode = xpp.next();
				}
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				break;

			case 1:
				
				saresearch = new MapListImageAndTextListAdapterRSre(
						ELAD_rereserch_List.this, listresearch, tabcontent_point_lin_research);
				
//				saresearch = new SimpleAdapter(ELAD_rereserch_List.this,
//						listresearch,
//						R.layout.elad_researchhistorylist,
//						new String[] { BasicProperties.XML_TAG136,
//								BasicProperties.XML_TAG135,
//								BasicProperties.XML_TAG133,
//								BasicProperties.XML_TAG134 }, new int[] {
//								R.id.pointtab_researchhistory_img,
//								R.id.pointtab_researchhistory_title,
//								R.id.pointtab_researchhistory_point,
//								R.id.pointtab_researchhistory_pointa });
//				saresearch.setViewBinder(new CustomViewBinder());
				tabcontent_point_lin_research.setAdapter(saresearch);

				break;

			}
		};
	};
	
}
