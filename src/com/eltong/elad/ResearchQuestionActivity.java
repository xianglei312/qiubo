package com.eltong.elad;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import com.RTalk.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.ClipboardManager;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ResearchQuestionActivity extends Activity {
	private ProgressDialog wait_dialog = null;
	private TextView texttitle = null;
	private LinearLayout research_question = null;
	private int points = 0;
	private int pointsa = 0;
	private ArrayList<ResearchQuestionValue> listr = new ArrayList<ResearchQuestionValue>();
	private Button research_question_button_submit = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_research_question);
		texttitle = (TextView) findViewById(R.id.research_question_textviewtitle);
		texttitle.setText(BasicProperties.title);

		research_question = (LinearLayout) findViewById(R.id.research_question);

		research_question_button_submit = (Button) findViewById(R.id.research_question_button_submit);
		research_question_button_submit
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						final String sb = doCommit();
						if (sb != null) {
							wait_dialog = AppliedMethod
									.showWaitDialog(ResearchQuestionActivity.this);
							// 新线程提交答�?
							new Thread() {
								public void run() {
									InputStream is = AppliedMethod
											.doAnswerPostSubmit(
													BasicProperties.HTTP_URL_InsertResearchAnswer,
													sb);
									
									int count = doReadCommitXML(is);
									Message msg = new Message();
									msg.what = 2;
									msg.obj = count;
									handler.sendMessage(msg);
								}
							}.start();
						}
					}
				});
		
		wait_dialog = AppliedMethod.showWaitDialog(this);
		new Thread() {
			public void run() {
				StringBuffer strParam = new StringBuffer();
				strParam.append("Research_IDX=");
				strParam.append(BasicProperties.Research_IDX);
				InputStream is = AppliedMethod.doPostSubmit(
						BasicProperties.HTTP_URL_GetResearchQuestion, strParam.toString());
				doReadListXML(is);
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		}.start();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:

			ResearchQuestionActivity.this.finish();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				break;
			case 1:
				doDisplay();
				break;
			case 2:
				doCommitResult(Integer.parseInt(msg.obj.toString()));
				break;
			}
		};
	};
	
	
	/**
	 * @author XXD
	 * @note 动�?显示问题
	 */
	private void doDisplay() {
		if (listr.size() == 0) {
			showToast(getString(R.string.toast_msg_question_without));

			ResearchQuestionActivity.this.finish();
		}
		for (int i = 0; i < listr.size(); i++) {
			ResearchQuestionValue rq = listr.get(i);
			if (BasicProperties.XML_VALUE8.equals(rq.Research_Type)) {
				View view = View.inflate(ResearchQuestionActivity.this,
						R.layout.elad_linearlayout_item_select, null);
				TextView tv = (TextView) view
						.findViewById(R.id.tv_question_select);
				tv.setText((i + 1)
						+ BasicProperties.SIGN
						+ rq.question
						+ " " +rq.Question_amount + getString(R.string.buy_fen));
				RadioGroup rg = (RadioGroup) view
						.findViewById(R.id.rg_answer_items);
				RadioButton rb;
					if(!rq.answer1.toString().equals(""))
					{
						rb = getRadioButton();
						rb.setText(rq.answer1);
						rg.addView(rb);
					}
					if(!rq.answer2.toString().equals(""))
					{
						rb = getRadioButton();
						rb.setText(rq.answer2);
						rg.addView(rb);
					}
					if(!rq.answer3.toString().equals(""))
					{
						rb = getRadioButton();
						rb.setText(rq.answer3);
						rg.addView(rb);
					}
					if(!rq.answer4.toString().equals(""))
					{
						rb = getRadioButton();
						rb.setText(rq.answer4);
						rg.addView(rb);
					}
					if(!rq.answer5.toString().equals(""))
					{
						rb = getRadioButton();
						rb.setText(rq.answer5);
						rg.addView(rb);
					}
					if(!rq.answer6.toString().equals(""))
					{
						rb = getRadioButton();
						rb.setText(rq.answer6);
						rg.addView(rb);
					}
					if(!rq.answer7.toString().equals(""))
					{
						rb = getRadioButton();
						rb.setText(rq.answer7);
						rg.addView(rb);
					}
					if(!rq.answer8.toString().equals(""))
					{
						rb = getRadioButton();
						rb.setText(rq.answer8);
						rg.addView(rb);
					}
					research_question.addView(view);
			} else if (BasicProperties.XML_VALUE9.equals(rq.Research_Type)) {
				View view = View.inflate(ResearchQuestionActivity.this,
						R.layout.elad_linearlayout_item_inter, null);
				TextView tv = (TextView) view
						.findViewById(R.id.tv_question_select);
				tv.setText((i + 1)
						+ BasicProperties.SIGN
						+ rq .question
						+ " " + rq.Question_amount + getString(R.string.buy_fen));
				EditText et = (EditText) view.findViewById(R.id.et_answer);
				et.setOnLongClickListener(new OnLongClickListener() {

					@Override
					public boolean onLongClick(View arg0) {
						// TODO Auto-generated method stub
						ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
						if (cm.hasText()) {
							cm.setText("");
						}
						return false;
					}
				});
				research_question.addView(view);
			}
		}
		wait_dialog.dismiss();
	}
	
	/**
	 * @author XXD
	 * @note 读取问题列表并装入List
	 */
	public void doReadListXML(InputStream is) {
		if (is == null) {
			Toast.makeText(getApplicationContext(),
					this.getString(R.string.toast_msg_request_error),
					Toast.LENGTH_SHORT).show();
		} else {
			try {
				ResearchQuestionValue rq = null;
				XmlPullParser xpp = Xml.newPullParser();
				xpp.setInput(is, BasicProperties.CODED_FORMAT);
				int eventCode = xpp.getEventType();
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					String nodeName = xpp.getName();
					switch (eventCode) {
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						if (BasicProperties.XML_TAG78.equals(nodeName)) {
							rq = new ResearchQuestionValue();
						} else if (BasicProperties.XML_TAG79.equals(nodeName)) {
							rq.Research_Question_IDX = xpp.nextText();
						} else if (BasicProperties.XML_TAG80.equals(nodeName)) {
							rq.Research_Type = xpp.nextText();
						} else if (BasicProperties.XML_TAG81.equals(nodeName)) {
							rq.Question_amount = xpp.nextText();
						} else if (BasicProperties.XML_TAG82.equals(nodeName)) {
							rq.question = xpp.nextText();
						} else if (BasicProperties.XML_TAG83.equals(nodeName)) {
							rq.least_length = xpp.nextText();
						} else if (BasicProperties.XML_TAG84.equals(nodeName)) {
							rq.answer1 = xpp.nextText();
						} else if (BasicProperties.XML_TAG85.equals(nodeName)) {
							rq.answer2 = xpp.nextText();
						} else if (BasicProperties.XML_TAG86.equals(nodeName)) {
							rq.answer3 = xpp.nextText();
						} else if (BasicProperties.XML_TAG87.equals(nodeName)) {
							rq.answer4 = xpp.nextText();
						} else if (BasicProperties.XML_TAG88.equals(nodeName)) {
							rq.answer5 = xpp.nextText();
						} else if (BasicProperties.XML_TAG89.equals(nodeName)) {
						    rq.answer6 = xpp.nextText();
						} else if (BasicProperties.XML_TAG90.equals(nodeName)) {
						    rq.answer7 = xpp.nextText();
						} else if (BasicProperties.XML_TAG91.equals(nodeName)) {
						    rq.answer8 = xpp.nextText();
					}
						break;
					case XmlPullParser.END_TAG:
						if (BasicProperties.XML_TAG18.equals(nodeName)) {
							listr.add(rq);
						}
						break;
					}
					eventCode = xpp.next();
				}
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				wait_dialog.dismiss();
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				wait_dialog.dismiss();
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * @author XXD
	 * @note Toast提示
	 */
	private void showToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
				.show();
	}

	
	/**
	 * @author XXD
	 * @note RadioButton实例
	 */
	private RadioButton getRadioButton() {
		RadioButton rb = new RadioButton(this);
		rb.setTextSize(BasicProperties.QUESTION_TEXT_SIZE);
		rb.setTextColor(getResources().getColor(R.color.silver));
		return rb;
	}
	
 	 /**
	 * @author XXD
	 * @note 问题提交，生成XML字符�?
	 */	
	private String doCommit() {
		pointsa=0;
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try{
			serializer.setOutput(writer);

			//<?xml version=�?.0�?encoding=”UTF-8�?standalone=”yes�?>
			serializer.startDocument("UTF-8", true); 
			 
			//<NewDataSet>***</NewDataSet>
			serializer.startTag("","NewDataSet");
			int size = listr.size();
			for (int i = 0; i < size; i++) {
				points=0;
				//<Table>***</Table>
				serializer.startTag("","Table");	
				ResearchQuestionValue qv = listr.get(i);
				//<member_IDX>***</member_IDX>
				serializer.startTag("","member_IDX");
				serializer.text(""+BasicProperties.MEMBER_ID);
				serializer.endTag("","member_IDX");
				
				serializer.startTag("","ResearchQuestion_IDX");
				serializer.text(qv.Research_Question_IDX);
				serializer.endTag("","ResearchQuestion_IDX");
				
				LinearLayout ll = (LinearLayout) research_question.getChildAt(i);
				if (BasicProperties.XML_VALUE8.equals(qv.Research_Type)) {
					RadioGroup rg = (RadioGroup) ll.getChildAt(1);
					if (rg.getCheckedRadioButtonId() == -1) {
						showToast(String.format(
								getString(R.string.toast_msg_cannot_null), i + 1));
						return null;
					} else {
						points = Integer.parseInt(qv.Question_amount);
						pointsa += Integer.parseInt(qv.Question_amount);
     					serializer.startTag("","answer");
						serializer.text(""+(rg.indexOfChild(findViewById(rg.getCheckedRadioButtonId()))+1));
						serializer.endTag("","answer");
						serializer.startTag("", "answer_text");
						serializer.text("");
						serializer.endTag("", "answer_text");
					}
				} else if (BasicProperties.XML_VALUE9.equals(qv.Research_Type)) {
					String text = ((EditText) ll.getChildAt(1)).getText()
							.toString();
					if ("".equals(text.toString().trim())) {
						showToast(String.format(
								getString(R.string.toast_msg_cannot_null), i + 1));
						return null;
					} else if (text.length() < Integer.parseInt(qv.least_length)) {
						showToast(String.format(
								getString(R.string.toast_msg_cannot_least), i + 1,
								Integer.parseInt(qv.least_length)));
						return null;
					} else {
						serializer.startTag("", "answer");
						serializer.text("");
						serializer.endTag("", "answer");
						points = Integer.parseInt(qv.Question_amount);
						pointsa += Integer.parseInt(qv.Question_amount);
						serializer.startTag("","answer_text");
						serializer.text(text);
						serializer.endTag("","answer_text");
					}
				}
				serializer.startTag("","money");
				serializer.text(""+points);
				serializer.endTag("","money");		
				
				serializer.startTag("","Research_IDX");
				serializer.text(""+BasicProperties.Research_IDX);
				serializer.endTag("","Research_IDX");		
				
				serializer.endTag("","Table");
			}
			serializer.endTag("","NewDataSet");

			serializer.endDocument();
			return writer.toString();

			}
			catch(Exception e)
			{
			throw new RuntimeException(e);
			}
	}
 	 
 	/**
 	 * @author XXD
 	 * @note 根据返回提交的状态跳�?
 	 */
 	private void doCommitResult(int count) {
 		
 		if (count == 0) {
 			Toast.makeText(
 					getApplicationContext(),
 					ResearchQuestionActivity.this
 							.getString(R.string.toast_msg_commit_failure),
 					Toast.LENGTH_SHORT).show();
 		} else if (count == 1) {
 			Toast.makeText(
 					getApplicationContext(),
 					String.format(ResearchQuestionActivity.this
 							.getString(R.string.toast_msg_commit_success),
 							pointsa), Toast.LENGTH_SHORT).show();

 			BasicProperties.Researchpoints = pointsa;
 			Intent intent = new Intent();
 			intent.setClass(ResearchQuestionActivity.this, ResearchResultActivity.class);

 			startActivity(intent);
 			
 			ResearchQuestionActivity.this.finish();

 		} else if (count == 2) {
 			Toast.makeText(
 					getApplicationContext(),
 					ResearchQuestionActivity.this.getString(R.string.toast_msg_canread),
 					Toast.LENGTH_SHORT).show();

 			ResearchQuestionActivity.this.finish();

 		}
 	}
 	
 	/**
 	 * @author XXD
 	 * @note 读取提交后返回的XML
 	 */
 	private int doReadCommitXML(InputStream is) {
 		int count = -1;
 		if (is == null) {
 			Message msg = new Message();
 			msg.what = 0;
 			msg.obj = this.getString(R.string.toast_msg_request_error);
 			handler.sendMessage(msg);
 		} else {
 			try {
 				XmlPullParser xpp = Xml.newPullParser();
 				xpp.setInput(is, BasicProperties.CODED_FORMAT);
 				int eventCode = xpp.getEventType();
 				while (eventCode != XmlPullParser.END_DOCUMENT) {
 					String nodeName = xpp.getName();
 					switch (eventCode) {
 					case XmlPullParser.START_DOCUMENT:
 						break;
 					case XmlPullParser.START_TAG:
 						if (BasicProperties.XML_TAG104.equals(nodeName)) {
 							return Integer.parseInt(xpp.nextText());
 						}
 						break;
 					case XmlPullParser.END_TAG:
 						break;
 					}
 					eventCode = xpp.next();
 				}
 			} catch (XmlPullParserException e) {
 				// TODO Auto-generated catch block
 				wait_dialog.dismiss();
 				e.printStackTrace();
 			} catch (IOException e) {
 				// TODO Auto-generated catch block
 				wait_dialog.dismiss();
 				e.printStackTrace();
 			}
 		}
 		return count;
 	}
}
