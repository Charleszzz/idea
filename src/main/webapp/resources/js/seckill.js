/**
 * Created by zhangyong on 16/7/14.
 */
//存放主要交互逻辑 模块化

var seckill={
    //封装秒杀相关ajax的url
    URL:{
        now : function(){
            return "/seckill/time/now";
        },
        exposer : function(seckillId){
            return "/seckill/"+seckillId+"/exposer";
        },
        execution : function(seckillId, md5){
            return '/seckill/'+seckillId+"/"+md5+"/execution";
        }
    },
    handleSeckillKill : function(seckillId, node){
        //获取秒杀地址,控制显示逻辑,执行秒杀
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.URL.exposer(seckillId), {}, function(result){
            //在回调函数中,执行交互流程
            if (result && result['success']){
                var exposer = result['t'];
                if (exposer){
                    if (exposer['exposered']){
                        //开启秒杀
                        var execution = result['t'];
                        var md5 = execution['md5'];
                        var killUrl = seckill.URL.execution(seckillId, md5);
                        console.log("killUrl = " + killUrl);
                        $('#killBtn').one('click',function(){
                            //执行秒杀请求
                            //1:禁用按钮
                            $(this).addClass('disabled');
                            //2:发送请求,执行秒杀
                            $.post(killUrl , {} , function(result){
                                if (result && result['success']){
                                    var killResult = result['t'];
                                    var state = killResult['state'];
                                    var stateInfo = killResult['stateInfo'];
                                    //3:显示秒杀结果
                                    node.html('<span class="label label-success">'+stateInfo+'</span>');
                                }
                            });
                        });
                        node.show();
                    }else {
                        //未开启秒杀,重新计时
                        var now = exposer['now'];
                        var start = exposer['startTime'];
                        var end = exposer['endTime'];
                        seckill.countdown(seckillId, now, start, end);
                    }
                }else {
                    console.log("result = "+result);
                }
            }
        });
    },
    //验证手机号
    validatePhone : function(phone){
        if (phone && phone.length == 11 && !isNaN(phone)){
            return true;
        }else {
            return false;
        }
    },
     countdown : function(seckillId, nowTime, startTime, endTime){
         var seckillBox = $('#seckill-box');
        if (nowTime > endTime){
            //秒杀结束
            seckillBox.html('秒杀结束!');
        }else if (nowTime < startTime){
            //秒杀未开始,计时事件绑定
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime,function(event){
                var format = event.strftime('秒杀倒计时: %D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown', function(){
                seckill.handleSeckillKill(seckillId, seckillBox);
            });
        }else {
            //秒杀开始
            seckill.handleSeckillKill(seckillId, seckillBox);
        }
    },
    //详情页秒杀逻辑
    detail:{
        //详情页初始化
        init: function(params){
            //手机验证和登录,计时交互
            //规划交互流程
            //在cookie 中查找手机号
            var killPhone = $.cookie('userPhone');
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];

            //验证手机号
            if (!seckill.validatePhone(killPhone)){
                //绑定phone
                //控制输出
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show : true,//显示弹出层
                    backdrop : 'static',//禁止位置关闭
                    keybarod : false//关闭键盘事件
                });
                $('#killPhoneBtn').click(function(){
                    var inputPhone = $('#killPhoneKey').val();
                    if (seckill.validatePhone(inputPhone)){
                        //电话写入cookie
                        $.cookie('userPhone', inputPhone, {expires: 7, path: '/seckill'});
                        //刷新页面
                        window.location.reload();
                    }else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误!</label>').show(300);
                    }
                });
            }
            //已经登录
            $.get(seckill.URL.now(), {}, function (result) {
                if (result && result['success']){
                    var now = result['t'];
                    seckill.countdown(seckillId, now, startTime, endTime);
                }else {
                    console.log("result=" + result)
                }
            });
        }
    }
}
