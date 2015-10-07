package org.yuner.www.chatter;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * �ı�����������
 * 
 * @author Reginer
 * 
 */
@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class TextManager {

	/**
	 * �����ı�
	 * 
	 * @param context
	 * @param message
	 *            // �����Ƶ��ı�
	 */
	public static void copyText(Context context, String message) {
		// ��ȡ������������
		ClipboardManager cmb = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		// ���ı����ݸ��Ƶ�������
		cmb.setText(message.trim());
	}

	/**
	 * ճ���ı�
	 * 
	 * @param context
	 * @return
	 */
	public static String pasteText(Context context) {
		// �õ������������
		ClipboardManager cmb = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		return cmb.getText().toString().trim();
	}

}