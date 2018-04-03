package com.lingfei.android.business.util;

public class CmdUtils{
				static byte head = (byte) 0xA0;

				//arm to mcu
				public static byte[] armshutdown = {head, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04}; //arm 要求关机
				public static byte[] arminitok = {head, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x05};  //arm 开机了
				public static byte[] storeok = {head, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x06}; //arm 会应mcu保存完毕信息
				public static byte[] gooldcar = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x01, (byte) 0x08}; //arm 通知mcu返回原车
				public static byte[] gohome = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x02, (byte) 0x09}; //arm 通知mcu返回主界面
				public static byte[] gobcamera = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x03, (byte) 0x0a}; //arm 通知mcu进入前视
				public static byte[] addbright = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x04, (byte) 0x0b}; //arm 通知mcu增加背光亮度
				public static byte[] subbright = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x05, (byte) 0x0c}; //arm 通知mcu减少背光亮度
				public static byte[] addcontrast = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x06, (byte) 0x0d}; //arm 通知mcu增加对比度
				public static byte[] subcontrast = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x07, (byte) 0x0e}; //arm 通知mcu减少对比度
				public static byte[] resetvideo = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x08, (byte) 0x0f}; //arm 通知mcu复位视频参数
				public static byte[] cameraset = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x09, (byte) 0x10}; //arm 摄像头设置按键
				public static byte[] godvd = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x0a, (byte) 0x11}; //arm 通知mcu进入dvd
				public static byte[] gocmmb = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x0b, (byte) 0x12}; //arm 通知mcu进入cmmb
				public static byte[] gogps = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x0c, (byte) 0x13}; //arm 通知mcu进入gps
				public static byte[] gousb = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x0d, (byte) 0x14}; //arm 通知mcu进入usb
				public static byte[] gosd = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x0e, (byte) 0x15}; //arm 通知mcu进入sd
				public static byte[] goipod = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x0f, (byte) 0x16}; //arm 通知mcu进入ipod
				public static byte[] gobtmusic = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x10, (byte) 0x17}; //arm 通知mcu进入蓝牙音乐
				public static byte[] gofzvideo = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x11, (byte) 0x18}; //arm 通知mcu进入辅助视频模式
				public static byte[] gocamera = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x12, (byte) 0x19}; //arm 通知mcu进入后视模式
				public static byte[] gobtspeek = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x13, (byte) 0x1a}; //arm 通知mcu进入 蓝牙通话
				public static byte[] gosetting = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x14, (byte) 0x1b}; //arm 通知mcu进入设置
				public static byte[] godvr = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x15, (byte) 0x1c}; //arm 通知mcu进入dvr
				public static byte[] go360 = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x16, (byte) 0x1d}; //arm 通知mcu进入360
				public static byte[] goklink = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x17, (byte) 0x1e}; //arm 通知mcu进入k-link
				public static byte[] gowan = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x18, (byte) 0x1f}; //arm 通知mcu进入娱乐
				public static byte[] gomode = {head, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x30, (byte) 0x37}; //arm 通知mcu进入mode
				public static byte[] gomhl = {head, (byte) 0x04, (byte) 0x00, (byte) 0x05, (byte) 0x00, (byte) 0x31, (byte) 0x3a}; //arm 通知mcu进入mhl

				public static byte[] no_valid_key = {(byte) 0xf0, (byte) 0x04, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0xff, (byte) 0x06};
												////arm 通知mcu无效 Key事件

				//=====================================================
				public static byte[] gpssoundplaying = {head, (byte) 0x04, (byte) 0x00, (byte) 0x05, (byte) 0x00, (byte) 0x01, (byte) 0x0a};
												//arm 通知mcu导航有声音   媒体无声音
				public static byte[] gpssoundplaying1 = {head, (byte) 0x04, (byte) 0x00, (byte) 0x05, (byte) 0x00, (byte) 0x05, (byte) 0x0e};
												//arm 通知mcu导航有声音   媒体有声音
				public static byte[] gpssoundnoplaying = {head, (byte) 0x04, (byte) 0x00, (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x09};
												//arm 通知mcu导航无声音   媒体没有声音
				public static byte[] gpssoundnoplaying1 = {head, (byte) 0x04, (byte) 0x00, (byte) 0x05, (byte) 0x00, (byte) 0x04, (byte) 0x0d};
												//arm 通知mcu导航无声音   媒体有声音
				//==============================================================
				public static byte[] talktp =
												{(byte) 0xf0, (byte) 0x08, (byte) 0x00, (byte) 0x0a, (byte) 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x09};
												//arm 通知mcu 触摸坐标 第一个ff表示按下状态
				//0x61表示第一次按 0xa1 连续按 0xe1 抬起 第2个ff 第三个ff 代表x坐标的低8位和高8位 依次类推


				public static byte[] cattype = {head, (byte) 0x05, (byte) 0x00, (byte) 0x0f, (byte) 0x00, (byte) 0xff, (byte) 0xff, (byte) 0x06};
												//arm 通知mcu 车型 第一个ff 的bit0 表示福特 bit1 表示宝马 bit2 表示奔驰 bit3
				//表示奥迪 bit4 表示路虎 bit5 表示沃尔沃 bit6 表示本田 bit7 表示大众  第2个ff bit0表示翼虎 bit1 表示蒙迪欧 bit2表示锐界
				public static byte[] mp5status = {head, (byte) 0x05, (byte) 0x00, (byte) 0x11, (byte) 0x00, (byte) 0xff, (byte) 0xff, (byte) 0x06};
												//第一个ff =0x00表示小屏 =0x01 表示大屏 第2个保留

				public static byte[] doneseting = {head, (byte) 0x05, (byte) 0x7b, (byte) 0x01, (byte) 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x06};
				//
				//第一个ff
				//Bit0：一键升窗关
				//Bit1：硬件倒车开
				//Bit2：行车记录仪开
				//Bit3：手机互联开   0-表示开关关  -1 表示开关开
				//Bit4： 宝马原车主机选择  0-表示哈曼主机  1-表示阿尔派主机
				//第2个ff 保留
				public static byte[] doneseting1 = {head, (byte) 0x05, (byte) 0x7b, (byte) 0x01, (byte) 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xf0};
				//第一个ff
				//Bit0~3：（DVR品牌）
				//0000: 宝马专用 0001：通用品牌1
				//0010：通用品牌2 依次类推
				//Bit4~7： （CMMB品牌
				//0000：品牌1 0001：品牌2
				//0010：通用品牌3 依次类推
				//第2个ff
				//Bit0~3（DVD品牌）
				//Bit4~7：保留
				//0000：品牌1 0001：品牌2
				//0010：通用品牌3 依次类推

				//mcu to arm
				public static byte[] mcushutdown = {head, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0xf0};//mcu 要求关机
				public static byte[] mcuinitok = {head, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0xf0}; //mcu 初始ok
				public static byte[] setoldcar = {head, (byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0xf0}; //设置成原车
				public static byte[] setnewcar = {head, (byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0xf0}; //设置成加装
				public static byte[] keyback = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x01, (byte) 0xf0}; //mcu  back
				public static byte[] keydown = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x02, (byte) 0xf0}; //mcu  down
				public static byte[] keyup = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x03, (byte) 0xf0}; //mcu  up
				public static byte[] keyenter = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x04, (byte) 0xf0}; //mcu  enter
				public static byte[] keyright = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x05, (byte) 0xf0}; //mcu  right
				public static byte[] keyleft = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x06, (byte) 0xf0}; //mcu  left
				public static byte[] keyseekb = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x07, (byte) 0xf0}; //mcu  seek+
				public static byte[] keyseekf = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x08, (byte) 0xf0}; //mcu  seek-
				public static byte[] backoldcar = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x09, (byte) 0xf0}; //mcu  要求返回原车
				public static byte[] enternewcar = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x0a, (byte) 0xf0}; //mcu  要求进入加装
				public static byte[] beforecomera = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x0b, (byte) 0xf0}; //mcu  要求进入前视
				public static byte[] intodvd = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x0c, (byte) 0xf0}; //mcu  要求进入dvd
				public static byte[] intocmmb = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x0d, (byte) 0xf0}; //mcu  要求进入cmmb
				public static byte[] intogps = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x0e, (byte) 0xf0}; //mcu  要求进入gps
				public static byte[] intousb = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x0f, (byte) 0xf0}; //mcu  要求进入usb
				public static byte[] intosd = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x10, (byte) 0xf0}; //mcu  要求进入sd
				public static byte[] intopod = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x11, (byte) 0xf0}; //mcu  要求进入ipod
				public static byte[] intobt = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x12, (byte) 0xf0}; //mcu  要求进入bt
				public static byte[] intootherav = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x13, (byte) 0xf0}; //mcu  要求进入辅助视频模式
				public static byte[] intodaoche = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x14, (byte) 0xf0}; //mcu  要求进入后视模式
				public static byte[] intobtspeek = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x15, (byte) 0xf0}; //mcu  要求进入bt通话模式
				public static byte[] intosetting = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x16, (byte) 0xf0}; //mcu  要求进入setting
				public static byte[] keymenu = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x17, (byte) 0xf0}; //mcu  menu
				public static byte[] keybtcall = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x18, (byte) 0xf0}; //mcu  蓝牙接听电话按键
				public static byte[] keybtover = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x19, (byte) 0xf0}; //mcu  蓝牙挂掉电话按键
				public static byte[] intodvr = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x20, (byte) 0xf0}; //mcu  进入dvr模式
				public static byte[] into360 = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x21, (byte) 0xf0}; //mcu  进入360模式
				public static byte[] intokeylink = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x22, (byte) 0xf0}; //mcu  进入k-link
				public static byte[] intogohigh = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x23, (byte) 0xf0}; //mcu  进入娱乐模式
				public static byte[] keyvolup = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x25, (byte) 0xf0}; //mcu
				public static byte[] keyvoldown = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x26, (byte) 0xf0}; //mcu
				public static byte[] intomhl = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x30, (byte) 0xf0}; //mcu 进入mhl模式
				public static byte[] intomhlback = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x31, (byte) 0xf0}; //mcu  mhl手机返回
				public static byte[] intomhlcalib = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x32, (byte) 0xf0}; //mcu  进入手机校准模式
				public static byte[] keyhome = {head, (byte) 0x04, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x33, (byte) 0xf0}; //mcu
				public static byte[] novideosigle = {head, (byte) 0x04, (byte) 0x00, (byte) 0x06, (byte) 0x01, (byte) 0x00, (byte) 0xf0}; //视频无信号
				public static byte[] onvideosigle = {head, (byte) 0x04, (byte) 0x00, (byte) 0x06, (byte) 0x01, (byte) 0x01, (byte) 0xf0}; //视频有信号
				public static byte[] onsource0 = {head, (byte) 0x04, (byte) 0x00, (byte) 0x07, (byte) 0x01, (byte) 0x00, (byte) 0xf0}; //当前没有播放源
				public static byte[] onsource1 = {head, (byte) 0x04, (byte) 0x00, (byte) 0x07, (byte) 0x01, (byte) 0x01, (byte) 0xf0}; //当前播放源是fm
				public static byte[] onsource2 = {head, (byte) 0x04, (byte) 0x00, (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0xf0}; //当前播放源是disc

				public static byte[] brightcontr = {(byte) 0xf0, (byte) 0x05, (byte) 0x00, (byte) 0x08, (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xf0};
												//第一个ff表示bright 0--99 第2个表示对比度 0--99

				public static byte[] checkswitch = {(byte) 0xf0, (byte) 0x04, (byte) 0x00, (byte) 0x0b, (byte) 0x01, (byte) 0xff, (byte) 0xf0};
												//第一个ff表示bit 0 dvr 检测 0表示没有检测到 1 表示检测到了
				//bit1 表示灯线检测 bit2 表示倒车线检测 bit3 表示ipod检测 bit4 表示dvd检测 bit5 表示cmmb检测  bit mhl检测 bit7 360检测

				//mcu发送给arm的设置菜单数据
				public static byte[] ansesetting = {(byte) 0xf0, (byte) 0x05, (byte) 0x00, (byte) 0x0e, (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xf0};
												//第一个ff表示bit 0 表示刹车关 1 表示开 bit1 表示hdmi设置为原厂 1 表示设置为导航
				// bit 2 表示camera  bit3 表示 轨迹 bit4 表示原车摄像头设置开关
				public static byte[] ansecattype = {(byte) 0xf0, (byte) 0x05, (byte) 0x00, (byte) 0x10, (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xf0};
												//第一个ff 的bit0 表示福特 bit1 表示宝马 bit2 表示奔驰 bit3
				//表示奥迪 bit4 表示路虎 bit5 表示沃尔沃 bit6 表示本田 bit7 表示大众  第2个ff bit0表示翼虎 bit1 表示蒙迪欧 bit2表示锐界
				public static byte[] anmp5status = {(byte) 0xf0, (byte) 0x05, (byte) 0x00, (byte) 0x12, (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xf0};
												//第一个ff =0x00表示小屏 =0x01 表示大屏 第2个保留

				public static byte[] fmfrep =
												{(byte) 0xf0, (byte) 0x07, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xf0};
												//第一个ff表示 0~5：FM1、FM2、FM3、AM1、AM2
				//第2个ff 0x01~0x06表示频率为相应预存台频率 0x00表示当前频点非预存台
				//第3个ff FM为乘以100后的低字节
				//第4个ff FM为乘以100后的高字节
				public static byte[] musicnumber = {(byte) 0xf0, (byte) 0x05, (byte) 0x02, (byte) 0x01, (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xf0};
												//第一个ff表示当前曲目低字节 第2个表示 高字节
				public static byte[] voicevaule = {(byte) 0xf0, (byte) 0x04, (byte) 0x7d, (byte) 0x01, (byte) 0x01, (byte) 0xff, (byte) 0xf0};//里面的ff表示音量值
				public static byte[] ktinfo =
												{(byte) 0xf0, (byte) 0x09, (byte) 0x7f, (byte) 0x00, (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xf0};
												//
				//第一个ff Bit 7 开关指示
				//0b:off, 1b: on
				//Bit 6 A/C 指示
				//0b:A/C off, 1b:A/C on
				//Bit 5 内外循环指示
				//0b:外循环 1b: 内循环
				//Bit 4 AUTO 指示
				//0b: off, 1b: on
				//Bit 3 前窗除雾
				//0b:OFF 1b: ON
				//Bit 2 后窗除雾
				//0b:OFF 1b: ON
				//Bit 1 ：
				//BMW3系（当Bit 5选择内循环模式）
				//0b: M 1b: A
				//第2个ff
				//Bit 7 向上送风开关(向头吹风)
				//0b:off, 1b: on
				//Bit 6 平行送风开关(向身体吹风)
				//0b:off, 1b: on
				//Bit 5 向下送风开关(向脚吹风)
				//0b:off, 1b: on
				//Bit 4 环绕送风开关
				//0b:off, 1b: on
				//Bit 3-Bit 0: 风量大小
				//0x00-0x07风量大小
				//第3个ff
				//0x00:Lo
				//0x1F:HI
				//0x01-0x1E: 15.5—30,步进 0.5；
				//第4个ff
				//0x00:Lo
				//0x1F:HI
				//0x01-0x1E: 15.5—30,步进 0.5；
				//第5个ff
				//Bit7-Bit5:
				//左边座椅加热
				//0-6级
				//Bit 4：warm max:
				//0b:off, 1b:on
				// Bit 3: Max A/C
				//0b:off, 1b:on
				//Bit2-Bit0:
				//右边座椅加热
				//0-6级
				//第6个ff
				//Bit 7-Bit 3: 0x00-0x09风量大小
				//Bit 2-Bit 0: NC
				public static byte[] rightktinfo =
												{(byte) 0xf0, (byte) 0x06, (byte) 0x7f, (byte) 0x03, (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xf0};
				//第一个ff
				//Bit 7 向上送风开关(向头吹风)
				//0b:off, 1b: on
				//Bit 6 平行送风开关(向身体吹风)
				//0b:off, 1b: on
				//Bit 5 向下送风开关(向脚吹风)
				//0b:off, 1b: on
				//Bit 4 环绕送风开关
				//0b:off, 1b: on
				//Bit 3-Bit 0: NC
				//第2个ff
				//Bit 7-Bit 3: 0x00-0x09风量大小
				//Bit 2-Bit 0: NC
				//第3个ff
				//0x00:Lo
				//0x1F:HI
				//0x01-0x1E: 15.5—30,步进 0.5；
				public static byte[] carobd = {(byte) 0xf0, (byte) 0x05, (byte) 0x7f, (byte) 0x01, (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xf0};
				//第一个ff
				//Bit 7 左前门开关指示
				//0b:off, 1b: on
				//Bit 6 右前门开关指示
				//0b:off, 1b: on
				//Bit 5 左后门开关指示
				//0b:off, 1b: on
				//Bit 4 右后门开关指示
				//0b:off, 1b: on
				//Bit 3:行李箱开关指示
				//0b:off, 1b: on
				//Bit 2 保留
				//Bit 1 保留
				//Bit 0 保留
				//第2个ff
				//0x00:PARK 档位
				//0x10:R 档位
				//0x20:N 档位
				//0x30:D/S 档位
				public static byte[] carlicheng =
												{(byte) 0xf0, (byte) 0x06, (byte) 0x7f, (byte) 0x02, (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xf0};
				//第一个ff
				//按顺序是高8 中8 低8
				public static byte[] ansedongsetting = {(byte) 0xf0, (byte) 0x05, (byte) 0x7b, (byte) 0x01, (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xf0};//
				////第一个ff
				//Bit0：一键升窗关
				//Bit1：硬件倒车开
				//Bit2：行车记录仪开
				//Bit3：手机互联开   0-表示开关关  -1 表示开关开
				//Bit4： 宝马原车主机选择  0-表示哈曼主机  1-表示阿尔派主机
				//第2个ff 保留
				public static byte[] ansedongsetting1 = {(byte) 0xf0, (byte) 0x05, (byte) 0x7b, (byte) 0x00, (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xf0};
												//
				//第一个字节
				//Bit0~3：（DVR品牌）
				//0000: 宝马专用 0001：通用品牌1
				//0010：通用品牌2 依次类推
				//Data0（Bit4~7：）
				//Bit4~7： （CMMB品牌）
				//0000：品牌1 0001：品牌2
				//0010：通用品牌3 依次类推
				//第2个ff
				//Bit0~3（DVD品牌）
				//Bit4~7：保留
				//0000：品牌1 0001：品牌2
				//0010：通用品牌3 依次类推


				public static byte[] gpssoundp_laying = {head, (byte) 0x04, (byte) 0x80, (byte) 0x0b, (byte) 0x00, (byte) 0x01, (byte) 0x90}; //arm 通知mcu导航有声音
				public static byte[] gpssound_noplaying = {head, (byte) 0x04, (byte) 0x80, (byte) 0x0b, (byte) 0x00, (byte) 0x00, (byte) 0x8f}; //arm 通知mcu导航无声音

				//activity
				public static byte[] activity_main = {head, (byte) 0x04, (byte) 0x80, (byte) 0x0a, (byte) 0x00, (byte) 0x00, (byte) 0x8e};
				public static byte[] activity_media = {head, (byte) 0x04, (byte) 0x80, (byte) 0x0a, (byte) 0x00, (byte) 0x01, (byte) 0x8f};
				public static byte[] activity_video = {head, (byte) 0x04, (byte) 0x80, (byte) 0x0a, (byte) 0x00, (byte) 0x02, (byte) 0x90};
				public static byte[] activity_music = {head, (byte) 0x04, (byte) 0x80, (byte) 0x0a, (byte) 0x00, (byte) 0x03, (byte) 0x91};
				public static byte[] activity_gps = {head, (byte) 0x04, (byte) 0x80, (byte) 0x0a, (byte) 0x00, (byte) 0x04, (byte) 0x92};
				public static byte[] activity_dvd = {head, (byte) 0x04, (byte) 0x80, (byte) 0x0a, (byte) 0x00, (byte) 0x05, (byte) 0x93};
				public static byte[] activity_tv = {head, (byte) 0x04, (byte) 0x80, (byte) 0x0a, (byte) 0x00, (byte) 0x06, (byte) 0x94};
				public static byte[] activity_phone = {head, (byte) 0x04, (byte) 0x80, (byte) 0x0a, (byte) 0x00, (byte) 0x07, (byte) 0x95};
				public static byte[] activity_web = {head, (byte) 0x04, (byte) 0x80, (byte) 0x0a, (byte) 0x00, (byte) 0x08, (byte) 0x96};
				public static byte[] activity_btmusic = {head, (byte) 0x04, (byte) 0x80, (byte) 0x0a, (byte) 0x00, (byte) 0x09, (byte) 0x97};
				public static byte[] activity_allapps = {head, (byte) 0x04, (byte) 0x80, (byte) 0x0a, (byte) 0x00, (byte) 0x0a, (byte) 0x98};
				public static byte[] activity_settings = {head, (byte) 0x04, (byte) 0x80, (byte) 0x0a, (byte) 0x00, (byte) 0x0b, (byte) 0x99};


				//多媒体
				public static byte[] media_godvd_s =
												{head, (byte) 0x06, (byte) 0x80, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x8f};
				public static byte[] media_gocmmb_s =
												{head, (byte) 0x06, (byte) 0x80, (byte) 0x06, (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x02, (byte) 0x90};
				public static byte[] media_godvd_m =
												{head, (byte) 0x06, (byte) 0x80, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x8e};
				public static byte[] media_gocmmb_m =
												{head, (byte) 0x06, (byte) 0x80, (byte) 0x06, (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x8f};
				public static byte[] media_godvd_l =
												{head, (byte) 0x06, (byte) 0x80, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x8d};
				public static byte[] media_gocmmb_l =
												{head, (byte) 0x06, (byte) 0x80, (byte) 0x06, (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x8e};


				public static byte[] media_exitdvd_s =
												{head, (byte) 0x06, (byte) 0x80, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x8e};
				public static byte[] media_exitcmmb_s =
												{head, (byte) 0x06, (byte) 0x80, (byte) 0x06, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x02, (byte) 0x8f};
				public static byte[] media_exitdvd_m =
												{head, (byte) 0x06, (byte) 0x80, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x8d};
				public static byte[] media_exitcmmb_m =
												{head, (byte) 0x06, (byte) 0x80, (byte) 0x06, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x8e};
				public static byte[] media_exitdvd_l =
												{head, (byte) 0x06, (byte) 0x80, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x8c};
				public static byte[] media_exitcmmb_l =
												{head, (byte) 0x06, (byte) 0x80, (byte) 0x06, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x8d};

				public static byte[] dvd_play = {head, (byte) 0x04, (byte) 0x80, (byte) 0x08, (byte) 0x00, (byte) 0x16, (byte) 0xa2};
				public static byte[] dvd_stop = {head, (byte) 0x04, (byte) 0x80, (byte) 0x08, (byte) 0x00, (byte) 0x17, (byte) 0xa3};
				public static byte[] dvd_prev = {head, (byte) 0x04, (byte) 0x80, (byte) 0x08, (byte) 0x00, (byte) 0x19, (byte) 0xa5};
				public static byte[] dvd_next = {head, (byte) 0x04, (byte) 0x80, (byte) 0x08, (byte) 0x00, (byte) 0x18, (byte) 0xa4};
				public static byte[] dvd_list = {head, (byte) 0x04, (byte) 0x80, (byte) 0x08, (byte) 0x00, (byte) 0x3f, (byte) 0xcb};
				public static byte[] dvd_sound = {head, (byte) 0x04, (byte) 0x80, (byte) 0x08, (byte) 0x00, (byte) 0x44, (byte) 0xd0};
				public static byte[] dvd_subtitle = {head, (byte) 0x04, (byte) 0x80, (byte) 0x08, (byte) 0x00, (byte) 0x45, (byte) 0xd1};
				public static byte[] dvd_forward = {head, (byte) 0x04, (byte) 0x80, (byte) 0x08, (byte) 0x00, (byte) 0x21, (byte) 0xad};
				public static byte[] dvd_rewind = {head, (byte) 0x04, (byte) 0x80, (byte) 0x08, (byte) 0x00, (byte) 0x27, (byte) 0xb3};

				public static byte[] cmmb_prev = {head, (byte) 0x04, (byte) 0x80, (byte) 0x07, (byte) 0x00, (byte) 0x52, (byte) 0xdd};
				public static byte[] cmmb_next = {head, (byte) 0x04, (byte) 0x80, (byte) 0x07, (byte) 0x00, (byte) 0x40, (byte) 0xcb};
				public static byte[] cmmb_ok = {head, (byte) 0x04, (byte) 0x80, (byte) 0x07, (byte) 0x00, (byte) 0x1d, (byte) 0xa8};
				public static byte[] cmmb_list = {head, (byte) 0x04, (byte) 0x80, (byte) 0x07, (byte) 0x00, (byte) 0x1a, (byte) 0xa5};
				public static byte[] cmmb_search = {head, (byte) 0x04, (byte) 0x80, (byte) 0x07, (byte) 0x00, (byte) 0x07, (byte) 0x92};
				public static byte[] cmmb_size1 = {head, (byte) 0x04, (byte) 0x80, (byte) 0x07, (byte) 0x00, (byte) 0x61, (byte) 0xec};
				public static byte[] cmmb_size2 = {head, (byte) 0x04, (byte) 0x80, (byte) 0x07, (byte) 0x00, (byte) 0x60, (byte) 0xeb};

				/*------高级设置-----*/
				//显示设置
				public static byte[] dis_brig_const = {head, (byte) 0x05, (byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x0f, (byte) 0x0f, (byte) 0xa3};
				public static byte[] dis_backlight_open = {head, (byte) 0x04, (byte) 0x80, (byte) 0x09, (byte) 0x00, (byte) 0x01, (byte) 0x8e};
				public static byte[] dis_backlight_close = {head, (byte) 0x04, (byte) 0x80, (byte) 0x09, (byte) 0x00, (byte) 0x00, (byte) 0x8d};

				public static byte[] nav_touch = {head, (byte) 0x05, (byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x86};
				public static byte[] nav_volume = {head, (byte) 0x05, (byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x86};

				public static byte[] nav_key_original = {head, (byte) 0x05, (byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x88};
				public static byte[] nav_key_add = {head, (byte) 0x05, (byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x89};

				public static byte[] senior_comfort_f =
												{head, (byte) 0x08, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x8a};


				public static byte[] senior_car_mode_1s = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x88};
				public static byte[] senior_car_mode_3s = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x89};
				public static byte[] senior_car_mode_5s = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x8a};
				public static byte[] senior_car_mode_1x = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x01, (byte) 0x03, (byte) 0x8b};
				public static byte[] senior_car_mode_3x = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x01, (byte) 0x04, (byte) 0x8c};
				public static byte[] senior_car_mode_5x = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x01, (byte) 0x05, (byte) 0x8d};

				public static byte[] senior_back_view1 = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x89};
				public static byte[] senior_back_view2 = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x8a};
				public static byte[] senior_back_view3 = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x02, (byte) 0x02, (byte) 0x8b};
				public static byte[] senior_back_view4 = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x02, (byte) 0x03, (byte) 0x8c};
				public static byte[] senior_back_view5 = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x02, (byte) 0x04, (byte) 0x8d};
				public static byte[] senior_back_view6 = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x02, (byte) 0x05, (byte) 0x8e};

				public static byte[] senior_input1 = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x8a};
				public static byte[] senior_input2 = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x03, (byte) 0x01, (byte) 0x8b};
				public static byte[] senior_input3 = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x03, (byte) 0x02, (byte) 0x8c};

				public static byte[] senior_dvd1 = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x8b};
				public static byte[] senior_dvd2 = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x04, (byte) 0x01, (byte) 0x8c};
				public static byte[] senior_dvd3 = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x04, (byte) 0x02, (byte) 0x8d};

				public static byte[] senior_cmmb1 = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x05, (byte) 0x00, (byte) 0x8c};
				public static byte[] senior_cmmb2 = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x05, (byte) 0x01, (byte) 0x8d};
				public static byte[] senior_cmmb3 = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x05, (byte) 0x02, (byte) 0x8e};

				public static byte[] senior_grapher1 = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x06, (byte) 0x00, (byte) 0x8d};
				public static byte[] senior_grapher2 = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x06, (byte) 0x01, (byte) 0x8e};
				public static byte[] senior_grapher3 = {head, (byte) 0x05, (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x06, (byte) 0x02, (byte) 0x8f};

				public static byte[] language_zh_cn = {head, (byte) 0x04, (byte) 0x80, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x87};
				public static byte[] language_zh_tw = {head, (byte) 0x04, (byte) 0x80, (byte) 0x03, (byte) 0x00, (byte) 0x01, (byte) 0x88};
				public static byte[] language_english = {head, (byte) 0x04, (byte) 0x80, (byte) 0x03, (byte) 0x00, (byte) 0x02, (byte) 0x89};

				public static byte[] reset_system = {head, (byte) 0x04, (byte) 0x80, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x88};
				public static byte[] reset_factory = {head, (byte) 0x04, (byte) 0x80, (byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x89};


				public static byte[] set_reboot = {head, (byte) 0x05, (byte) 0x7b, (byte) 0x00, (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xf0};//
				public static byte[] set_reset = {head, (byte) 0x05, (byte) 0x7b, (byte) 0x00, (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xf0};//
				public static byte[] set_h = {head, (byte) 0x05, (byte) 0x7b, (byte) 0x00, (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xf0};//

}
