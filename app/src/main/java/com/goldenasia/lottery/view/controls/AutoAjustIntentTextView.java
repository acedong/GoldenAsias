package com.goldenasia.lottery.view.controls;

import android.content.Context;
import android.graphics.Paint;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

public class AutoAjustIntentTextView extends TextView {

	public AutoAjustIntentTextView(Context context) {
		super(context);
	}
	
//	"<html><head><title>TextView使用HTML</title></head><body><p><strong>强调</strong></p><p><em>斜体</em></p>"  
//    +"<p><a href=\"http://www.dreamdu.com/xhtml/\">超链接HTML入门</a>学习HTML!</p><p><font color=\"#aabb00\">颜色1"  
//    +"</p><p><font color=\"#00bbaa\">颜色2</p><h1>标题1</h1><h3>标题2</h3><h6>标题3</h6><p>大于>小于<</p><p>" +  
//    "下面是网络图片</p><img src=\"http://avatar.csdn.net/0/3/8/2_zhang957411207.jpg\"/><br>" +
//    "<pre>  下面是网络图片</pre>" +
//    "</body></html>"

	public AutoAjustIntentTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		String intentSpace = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		String info = (String) super.getText();
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<body>");
		sb.append("<pre>");
		sb.append(intentSpace);
		sb.append(info);
		sb.append("</pre>");
		sb.append("</body>");
		sb.append("</html>");
		super.setText(Html.fromHtml(sb.toString()));
		
	}
}
