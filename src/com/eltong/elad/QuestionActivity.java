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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.ClipboardManager;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author XXD
 * @note 问题Activity
 */
public class QuestionActivity extends Activity {

	private int points = 0;
	private int pointsa = 0;
	private int anwserresult = 0;
	private String content_id = null;
	private ProgressDialog wait_dialog = null;
	private LinearLayout lin_question = null;
	private Button btn_question_commit = null;
	private ArrayList<QuestionValue> list = new ArrayList<QuestionValue>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_question);
		Bundle bundle = getIntent().getExtras();
		content_id = bundle.getString(BasicProperties.INTENT_KEY3);
		lin_question = (LinearLayout) findViewById(R.id.lin_question);
		btn_question_commit = (Button) findViewById(R.id.btn_question_commit);
		btn_question_commit.setOnClickListener(cl);
		wait_dialog = AppliedMethod.showWaitDialog(this);
		new Thread() {
			public void run() {
				StringBuffer strParam = new StringBuffer();
				strParam.append("AD_IDX=");
				strParam.append(content_id);
				InputStream is = AppliedMethod.doPostSubmit(
						BasicProperties.HTTP_URL_QUESTION, strParam.toString());
				doReadListXML(is);
				Message msg = new Message();
				msg.what = 1;

				handler.sendMessage(msg);
			}
		}.start();
	}

	private OnClickListener cl = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			int id = arg0.getId();
			if (id == R.id.btn_question_commit) {
				final String sb = doCommit();
				if (sb != null) {
					wait_dialog = AppliedMethod
							.showWaitDialog(QuestionActivity.this);
					// 新线程提交答�?
					new Thread() {
						public void run() {
							InputStream is = AppliedMethod.doAnswerPostSubmit(
									BasicProperties.HTTP_URL_COMMIT, sb);
							int count = doReadCommitXML(is);
							Message msg = new Message();
							msg.what = 2;
							msg.obj = count;
							handler.sendMessage(msg);
						}
					}.start();
				}
			}
//			switch (arg0.getId()) {
//			case R.id.btn_question_commit:
//				final String sb = doCommit();
//				if (sb != null) {
//					wait_dialog = AppliedMethod
//							.showWaitDialog(QuestionActivity.this);
//					// 新线程提交答�?
//					new Thread() {
//						public void run() {
//							InputStream is = AppliedMethod.doAnswerPostSubmit(
//									BasicProperties.HTTP_URL_COMMIT, sb);
//							int count = doReadCommitXML(is);
//							Message msg = new Message();
//							msg.what = 2;
//							msg.obj = count;
//							handler.sendMessage(msg);
//						}
//					}.start();
//				}
//				break;
//
//			case R.id.btn_question_cancel:
//				showDialog();
//				break;
//			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			AlertDialog alertDialog = new AlertDialog.Builder(this)
					.setTitle(getString(R.string.ad_val_title2))
					.setMessage(getString(R.string.ad_val_message4))
					.setPositiveButton(getString(R.string.ad_val_ok),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									Intent intent = new Intent();
									intent.setClass(QuestionActivity.this,
											ELAD_AD_List.class);
									startActivity(intent);
									QuestionActivity.this.finish();
								}
							})
					.setNegativeButton(getString(R.string.ad_val_cancle),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									return;
								}
							}).create();
			alertDialog.show();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			wait_dialog.dismiss();
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
	 * @note 根据返回提交的状态跳�?
	 */
	private void doCommitResult(int count) {
		
		if (count == 0) {
			Toast.makeText(
					getApplicationContext(),
					QuestionActivity.this
							.getString(R.string.toast_msg_commit_failure),
					Toast.LENGTH_SHORT).show();
		} else if (count == 1) {

			BasicProperties.content_id = Integer.parseInt(content_id);
			BasicProperties.points = pointsa;
			BasicProperties.anwserresult = anwserresult;
			Intent intent = new Intent();

			intent.setClass(QuestionActivity.this, QuestionResultActivity.class);

			startActivity(intent);

			QuestionActivity.this.finish();

		} else if (count == 2) {
			Toast.makeText(
					getApplicationContext(),
					QuestionActivity.this.getString(R.string.toast_msg_canread),
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			intent.setClass(QuestionActivity.this, ELAD_AD_List.class);

			startActivity(intent);

			QuestionActivity.this.finish();

		}
	}

	/**
	 * @author XXD
	 * @note 读取提交后返回的XML并跳�?
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
						if (BasicProperties.XML_TAG102.equals(nodeName)) {
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

	/**
	 * @author XXD
	 * @note 问题提交，生成XML字符�?
	 */
	private String doCommit() {
		pointsa = 0;
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);

			// <?xml version=�?.0�?encoding=”UTF-8�?standalone=”yes�?>
			serializer.startDocument("UTF-8", true);

			// <NewDataSet>***</NewDataSet>
			serializer.startTag("", "NewDataSet");
			int size = list.size();
			for (int i = 0; i < size; i++) {
				points = 0;
				// <Table>***</Table>
				serializer.startTag("", "Table");
				QuestionValue qv = list.get(i);
				// <member_IDX>***</member_IDX>
				serializer.startTag("", "member_IDX");
				serializer.text("" + BasicProperties.MEMBER_ID);
				serializer.endTag("", "member_IDX");

				serializer.startTag("", "ADQuestion_IDX");
				serializer.text(qv.ADQuestion_IDX);
				serializer.endTag("", "ADQuestion_IDX");

				LinearLayout ll = (LinearLayout) lin_question.getChildAt(i);
				if (BasicProperties.XML_VALUE8.equals(qv.Qunetion_type)) {
					RadioGroup rg = (RadioGroup) ll.getChildAt(1);
					if (rg.getCheckedRadioButtonId() == -1) {
						showToast(String.format(
								getString(R.string.toast_msg_cannot_null),
								i + 1));
						return null;
					} else {
						if (BasicProperties.XML_VALUE10.equals(qv.skip_flag)) {
							if ((rg.indexOfChild(findViewById(rg
									.getCheckedRadioButtonId())) + 1) == Integer
									.parseInt(qv.answer)) {
								points = Integer.parseInt(qv.question_amount);
								pointsa += Integer.parseInt(qv.question_amount);
								anwserresult += 1;
							} else {
								showToast(String
										.format(getString(R.string.toast_msg_cannot_wrong),
												i + 1));
								return null;
							}
						} else {
							if ((rg.indexOfChild(findViewById(rg
									.getCheckedRadioButtonId())) + 1) == Integer
									.parseInt(qv.answer)) {
								points = Integer.parseInt(qv.question_amount);
								pointsa += Integer.parseInt(qv.question_amount);
								anwserresult += 1;
							}
						}
						serializer.startTag("", "answer");
						serializer.text(""
								+ (rg.indexOfChild(findViewById(rg
										.getCheckedRadioButtonId())) + 1));
						serializer.endTag("", "answer");
						serializer.startTag("", "answer_content");
						serializer.text("");
						serializer.endTag("", "answer_content");
					}
				} else if (BasicProperties.XML_VALUE9.equals(qv.Qunetion_type)) {
					String text = ((EditText) ll.getChildAt(1)).getText()
							.toString();
					if ("".equals(text.toString().trim())) {
						showToast(String.format(
								getString(R.string.toast_msg_cannot_null),
								i + 1));
						return null;
					} else if (text.length() < Integer
							.parseInt(qv.least_length)) {
						showToast(String.format(
								getString(R.string.toast_msg_cannot_least),
								i + 1, Integer.parseInt(qv.least_length)));
						return null;
					} else {
						points = Integer.parseInt(qv.question_amount);
						pointsa += Integer.parseInt(qv.question_amount);
						anwserresult += 1;
						serializer.startTag("", "answer");
						serializer.text("");
						serializer.endTag("", "answer");
						serializer.startTag("", "answer_content");
						serializer.text(text);
						serializer.endTag("", "answer_content");
					}
				}
				serializer.startTag("", "money");
				serializer.text("" + points);
				serializer.endTag("", "money");

				serializer.startTag("", "AD_IDX");
				serializer.text(qv.AD_IDX);
				serializer.endTag("", "AD_IDX");

				serializer.endTag("", "Table");
			}
			serializer.endTag("", "NewDataSet");

			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
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
	 * @note 动�?显示问题
	 */
	private void doDisplay() {

		if (list.size() == 0) {
			showToast(getString(R.string.toast_msg_question_without));
			Intent intent = new Intent();
			intent.setClass(QuestionActivity.this, ELAD_AD_List.class);
			//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			QuestionActivity.this.finish();
		}
		for (int i = 0; i < list.size(); i++) {
			QuestionValue qv = list.get(i);
			if (BasicProperties.XML_VALUE8.equals(qv.Qunetion_type)) {
				View view = View.inflate(QuestionActivity.this,
						R.layout.elad_linearlayout_item_select, null);
				TextView tv = (TextView) view
						.findViewById(R.id.tv_question_select);
				tv.setText((i + 1)
						+ BasicProperties.SIGN
						+ qv.question
						+ " " + qv.question_amount + getString(R.string.buy_fen));
				RadioGroup rg = (RadioGroup) view
						.findViewById(R.id.rg_answer_items);
				RadioButton rb;
				if (!qv.answer1.toString().equals("")) {
					rb = getRadioButton();
					rb.setText(qv.answer1);
					rg.addView(rb);
				}
				if (!qv.answer2.toString().equals("")) {
					rb = getRadioButton();
					rb.setText(qv.answer2);
					rg.addView(rb);
				}
				if (!qv.answer3.toString().equals("")) {
					rb = getRadioButton();
					rb.setText(qv.answer3);
					rg.addView(rb);
				}
				if (!qv.answer4.toString().equals("")) {
					rb = getRadioButton();
					rb.setText(qv.answer4);
					rg.addView(rb);
				}
				if (!qv.answer5.toString().equals("")) {
					rb = getRadioButton();
					rb.setText(qv.answer5);
					rg.addView(rb);
				}
				lin_question.addView(view);
			} else if (BasicProperties.XML_VALUE9.equals(qv.Qunetion_type)) {
				View view = View.inflate(QuestionActivity.this,
						R.layout.elad_linearlayout_item_inter, null);
				TextView tv = (TextView) view
						.findViewById(R.id.tv_question_select);
				tv.setText((i + 1)
						+ BasicProperties.SIGN
						+ qv.question
						+ " " + qv.question_amount + getString(R.string.buy_fen));
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
				lin_question.addView(view);
			}
		}
		wait_dialog.dismiss();
	}

	/**
	 * @author 赵靖�?
	 * @note 读取问题列表并装入List
	 */
	public void doReadListXML(InputStream is) {
		if (is == null) {
			Toast.makeText(getApplicationContext(),
					this.getString(R.string.toast_msg_request_error),
					Toast.LENGTH_SHORT).show();
		} else {
			try {
				QuestionValue qv = null;
				XmlPullParser xpp = Xml.newPullParser();
				xpp.setInput(is, BasicProperties.CODED_FORMAT);
				int eventCode = xpp.getEventType();
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					String nodeName = xpp.getName();
					switch (eventCode) {
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						if (BasicProperties.XML_TAG18.equals(nodeName)) {
							qv = new QuestionValue();
						} else if (BasicProperties.XML_TAG19.equals(nodeName)) {
							qv.ADQuestion_IDX = xpp.nextText();
						} else if (BasicProperties.XML_TAG20.equals(nodeName)) {
							qv.Qunetion_type = xpp.nextText();
						} else if (BasicProperties.XML_TAG22.equals(nodeName)) {
							qv.skip_flag = xpp.nextText();
						} else if (BasicProperties.XML_TAG23.equals(nodeName)) {
							qv.question_amount = xpp.nextText();
						} else if (BasicProperties.XML_TAG24.equals(nodeName)) {
							qv.AD_IDX = xpp.nextText();
						} else if (BasicProperties.XML_TAG25.equals(nodeName)) {
							qv.least_length = xpp.nextText();
						} else if (BasicProperties.XML_TAG26.equals(nodeName)) {
							qv.answer = xpp.nextText();
						} else if (BasicProperties.XML_TAG27.equals(nodeName)) {
							qv.question = xpp.nextText();
						} else if (BasicProperties.XML_TAG28.equals(nodeName)) {
							qv.answer1 = xpp.nextText();
						} else if (BasicProperties.XML_TAG29.equals(nodeName)) {
							qv.answer2 = xpp.nextText();
						} else if (BasicProperties.XML_TAG30.equals(nodeName)) {
							qv.answer3 = xpp.nextText();
						} else if (BasicProperties.XML_TAG31.equals(nodeName)) {
							qv.answer4 = xpp.nextText();
						} else if (BasicProperties.XML_TAG311.equals(nodeName)) {
							qv.answer5 = xpp.nextText();
						}
						break;
					case XmlPullParser.END_TAG:
						if (BasicProperties.XML_TAG18.equals(nodeName)) {
							list.add(qv);
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
	 * @note RadioButton实例
	 */
	private RadioButton getRadioButton() {
		RadioButton rb = new RadioButton(this);
		rb.setTextSize(BasicProperties.QUESTION_TEXT_SIZE);
		rb.setTextColor(getResources().getColor(R.color.silver));
		return rb;
	}
}
