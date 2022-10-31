<%@ taglib prefix="s" uri="/struts-tags"%>
<!-- webTransaction.timercontrol.js  file holds the logic that relates to timer operation and popup -->
<script src="js/webTransaction.timercontrol.js" type="text/javascript"></script>
<div>
    <font color="red"><span id="timer"></span></font>
</div>
<div id="extendAlert" class="popPendingMTNTAlert">
    <div class="popClose" onclick="disableExtendAlert()"></div>
    <div id="pendingExtendAlertErrMsg" class="popPendingMTNTAlertErrMsg" align="center"></div>
    <div id="defaultExtendAlert" style="display: block;">
        <table align="center">
            <tr>
                <td>
                    <s:text name="confirm.delete" />
                </td>
            </tr>
            <tr>
                <td>
                    <s:text name="time" />
                    <span id="timer1"></span>
                </td>
            </tr>	
            <tr>
                <td style="padding-top: 10px;"><input type="button" id="extend"
                                                      class="popBtn" value="<s:text name="ok"/>"
                                                      onclick="extendTransaction()" /> <input id="extendCancel"
                                                      type="button" class="popBtn" value="<s:text name="cancel"/>"
                                                      onclick="disableExtendAlert()" /></td>
            </tr>
        </table>
    </div>
</div>
<div id="restartAlert" class="popPendingMTNTAlert">
    <div class="popClose" onclick="disableExtendAlert()"></div>
    <div id="pendingRestartAlertErrMsg" class="popPendingMTNTAlertErrMsg" align="center"></div>
    <div id="defaultRestartAlert" style="display: block;">
        <table align="center" id="restartTable"> 
            <tr>
                <td>
                    <s:text name="confirm.continue" />
                </td>
            </tr>
            <tr>
                <td><s:text name="time" /><span id="timer2"></span></td>
            </tr>
            <tr>
                <td style="padding-top: 10px;"><input type="button" id="restart"
                                                      class="popBtn" value="<s:text name="yes"/>"
                                                      onclick="disableExtendAlert();" /> <input id="restartCancel"
                                                      type="button" class="popBtn" value="<s:text name="no"/>"
                                                      onclick="redirectForm()" /></td>
            </tr>
        </table>
    </div>
</div>
<s:form name="cancelform" method="post">
    <s:hidden name="agentId"/>
    <s:hidden id="cancelFormAgentTimer" name="agentTimer" value=""/>
</s:form>
<s:form name="redirectform" method="post">
    <s:hidden name="agentId"/>
    <s:hidden id="redirectFormAgentTimer" name="agentTimer" value=""/>
</s:form>
<script>
    setFields(<s:property value="timerCount"/>, '<s:property value="agentId"/>', '<s:text name="expiryTime"/>');
</script>	
<s:if test="reStartTxn == true">
    <script>
        jQuery(document).ready(function () {
            enableRestartAlert();
        });
    </script>
</s:if>