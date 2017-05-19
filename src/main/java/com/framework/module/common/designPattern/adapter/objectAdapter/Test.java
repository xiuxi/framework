package com.framework.module.common.designPattern.adapter.objectAdapter;

/**
 * 适配器模式 测试(对象适配器: 在适配器里持有一个被适配类对象的引用)
 * 
 * [适配器模式(Adapter)把一个类的接口(Adaptee)变换成客户端所期待的另一种接口(Target)，从而使原本因接口不匹配而无法在一起工作的两个类能够在一起工作]
 * 
 * @author qq
 */
public class Test {
	/*
	 * 案例说话
	 * 
	 * 我是一个客服,现在公司业务拓展到了国外,有时要和老外通话,但我(Target)只会说中文(Target.speekChinese),
	 * 原本我可以自学英语(如果Target是类就继承它并重写speekChinese为speekEnglish,
	 * 如果Target是接口就实现一个speekEnglish)。但我不想学英文(不想修改Target)。因为我知道我儿子(Adapter)的同学小明
	 * (Adaptee)会说英文(Adaptee.speekEnglish)(已经有人实现了这个功能,我懒,直接拿来用,但是因为我不认识小明,
	 * 我不能直接和小明一起工作,只能通过我的儿子这个桥梁来认识小明),
	 * 当我要和老外打电话交流时就通过我儿子把小明叫来当翻译(我要说什么小明都会帮我用英文说,对于老外来说我也是能说英文的)
	 * 当我要和中国客户交流时就用我自己的中文交流就可以了(自己原有的实现)
	 */
	public static void main(String[] args) {
		// 和中国人交流
		My my1 = new My();
		my1.speekChinese();

		// 和老外交流
		My my2 = new MySon();
		my2.speekChinese();
	}
}
