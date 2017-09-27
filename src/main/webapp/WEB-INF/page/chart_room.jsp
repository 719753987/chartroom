<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <title>jquery仿微信聊天对话窗口滚动样式</title>

    <link rel="stylesheet" type="text/css" href="/css/jscrollpane1.css"/>
    <script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script>
    <!--引用jquery-1.4.2.min.js会影响添加表情，不引用jquery-1.4.2.min.js就不支持IE、火狐浏览器的鼠标滚轮插件-->
    <!-- the mousewheel plugin -->
    <script type="text/javascript" src="/js/jquery.mousewheel.js"></script>
    <!-- the jScrollPane script -->
    <script type="text/javascript" src="/js/jquery.jscrollpane.min.js"></script>
    <script type="text/javascript" src="/js/scroll-startstop.events.jquery.js"></script>
    <!--sockjs-->
    <script type="text/javascript" src="/js/sockjs.min.js"></script>
    <%--<script type="text/javascript" src="http://cdn.jsdelivr.net/sockjs/1.0.0/sockjs.min.js"></script>--%>
    <script type="text/javascript" src="/js/chart_room.js"></script>

</head>
<body>

<br>
<div class="talk">
    <div class="talk_title"><span>聊天室</span><span id="onlineCount"></span></div>
    <div class="talk_record">
        <div id="jp-container" class="jp-container">
            <%--<div class="talk_recordbox">
                <div class="user"><img src="/images/thumbs/15.jpg"/>美美</div>
                <div class="talk_recordtextbg">&nbsp;</div>
                <div class="talk_recordtext">
                    <h3>welcome!</h3>
                    <span class="talk_time">2014-09-15 15:06</span>
                </div>
            </div>--%>
        </div>
    </div>

    <div id="tool_bar" class="talk_word">
        &nbsp;
        <%--<input class="add_face" id="facial" type="button" title="添加表情" value="" />--%>
        <input id="username" type="hidden" value='${username}'/>
        <input id="name" type="hidden" value="${name}"/>
        <input id="content" class="messages emotion" autocomplete="off" placeholder="在这里输入文字"/>
        <input class="talk_send" type="button" title="发送" value="发送"/>
    </div>
</div>


<script type="text/javascript">
    $(document).ready(function () {

        // the element we want to apply the jScrollPane
        var $el = $('#jp-container').jScrollPane({
                verticalGutter: -16,
                autoReinitialise: true,
                stickToBottom: true
            }),

            // the extension functions and options
            extensionPlugin = {

                extPluginOpts: {
                    // speed for the fadeOut animation
                    mouseLeaveFadeSpeed: 500,
                    // scrollbar fades out after hovertimeout_t milliseconds
                    hovertimeout_t: 1000,
                    // if set to false, the scrollbar will be shown on mouseenter and hidden on mouseleave
                    // if set to true, the same will happen, but the scrollbar will be also hidden on mouseenter after "hovertimeout_t" ms
                    // also, it will be shown when we start to scroll and hidden when stopping
                    useTimeout: true,
                    // the extension only applies for devices with width > deviceWidth
                    deviceWidth: 980
                },
                hovertimeout: null, // timeout to hide the scrollbar
                isScrollbarHover: false,// true if the mouse is over the scrollbar
                elementtimeout: null,	// avoids showing the scrollbar when moving from inside the element to outside, passing over the scrollbar
                isScrolling: false,// true if scrolling
                addHoverFunc: function () {

                    // run only if the window has a width bigger than deviceWidth
                    if ($(window).width() <= this.extPluginOpts.deviceWidth) return false;

                    var instance = this;

                    // functions to show / hide the scrollbar
                    $.fn.jspmouseenter = $.fn.show;
                    $.fn.jspmouseleave = $.fn.fadeOut;

                    // hide the jScrollPane vertical bar
                    var $vBar = this.getContentPane().siblings('.jspVerticalBar').hide();

                    /*
                     * mouseenter / mouseleave events on the main element
                     * also scrollstart / scrollstop - @James Padolsey : http://james.padolsey.com/javascript/special-scroll-events-for-jquery/
                     */
                    $el.bind('mouseenter.jsp', function () {

                        // show the scrollbar
                        $vBar.stop(true, true).jspmouseenter();

                        if (!instance.extPluginOpts.useTimeout) return false;

                        // hide the scrollbar after hovertimeout_t ms
                        clearTimeout(instance.hovertimeout);
                        instance.hovertimeout = setTimeout(function () {
                            // if scrolling at the moment don't hide it
                            if (!instance.isScrolling)
                                $vBar.stop(true, true).jspmouseleave(instance.extPluginOpts.mouseLeaveFadeSpeed || 0);
                        }, instance.extPluginOpts.hovertimeout_t);


                    }).bind('mouseleave.jsp', function () {

                        // hide the scrollbar
                        if (!instance.extPluginOpts.useTimeout)
                            $vBar.stop(true, true).jspmouseleave(instance.extPluginOpts.mouseLeaveFadeSpeed || 0);
                        else {
                            clearTimeout(instance.elementtimeout);
                            if (!instance.isScrolling)
                                $vBar.stop(true, true).jspmouseleave(instance.extPluginOpts.mouseLeaveFadeSpeed || 0);
                        }

                    });

                    if (this.extPluginOpts.useTimeout) {

                        $el.bind('scrollstart.jsp', function () {

                            // when scrolling show the scrollbar
                            clearTimeout(instance.hovertimeout);
                            instance.isScrolling = true;
                            $vBar.stop(true, true).jspmouseenter();

                        }).bind('scrollstop.jsp', function () {

                            // when stop scrolling hide the scrollbar (if not hovering it at the moment)
                            clearTimeout(instance.hovertimeout);
                            instance.isScrolling = false;
                            instance.hovertimeout = setTimeout(function () {
                                if (!instance.isScrollbarHover)
                                    $vBar.stop(true, true).jspmouseleave(instance.extPluginOpts.mouseLeaveFadeSpeed || 0);
                            }, instance.extPluginOpts.hovertimeout_t);

                        });

                        // wrap the scrollbar
                        // we need this to be able to add the mouseenter / mouseleave events to the scrollbar
                        var $vBarWrapper = $('<div/>').css({
                            position: 'absolute',
                            left: $vBar.css('left'),
                            top: $vBar.css('top'),
                            right: $vBar.css('right'),
                            bottom: $vBar.css('bottom'),
                            width: $vBar.width(),
                            height: $vBar.height()
                        }).bind('mouseenter.jsp', function () {

                            clearTimeout(instance.hovertimeout);
                            clearTimeout(instance.elementtimeout);

                            instance.isScrollbarHover = true;

                            // show the scrollbar after 100 ms.
                            // avoids showing the scrollbar when moving from inside the element to outside, passing over the scrollbar
                            instance.elementtimeout = setTimeout(function () {
                                $vBar.stop(true, true).jspmouseenter();
                            }, 100);

                        }).bind('mouseleave.jsp', function () {

                            // hide the scrollbar after hovertimeout_t
                            clearTimeout(instance.hovertimeout);
                            instance.isScrollbarHover = false;
                            instance.hovertimeout = setTimeout(function () {
                                // if scrolling at the moment don't hide it
                                if (!instance.isScrolling)
                                    $vBar.stop(true, true).jspmouseleave(instance.extPluginOpts.mouseLeaveFadeSpeed || 0);
                            }, instance.extPluginOpts.hovertimeout_t);

                        });

                        $vBar.wrap($vBarWrapper);

                    }

                }

            },

            // the jScrollPane instance
            jspapi = $el.data('jsp');

        // extend the jScollPane by merging
        $.extend(true, jspapi, extensionPlugin);
        jspapi.addHoverFunc();

    });
</script>


</body>
</html>