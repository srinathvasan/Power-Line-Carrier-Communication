<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>POWER LINE CARRIER COMMUNICATION</title>
        <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/default/easyui.css">
        <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/icon.css">
        <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/color.css">
        <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/demo/demo.css">
        <script type="text/javascript" src="http://code.jquery.com/jquery-3.0.0.min.js"></script>
        <script type="text/javascript" src="http://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>
    </head>
    <body>

        <div style="margin:20px 0;">
            <center><h1>PLCC(Power Line Carrier Communication)</h1></center>
        </div>
        <div id="tabs" class="easyui-tabs" fit="true" border="false" plain="true" >
            <div title="User" style="padding:10px" data-options="selected:true">
                <table id="dg" class= "easyui-datagrid"
                       url="SelectUserData"
                       toolbar="#toolbar" pagination="true"
                       rownumbers="true" fitColumns="true" singleSelect="true" idField="userage">
                    <thead>
                        <tr>
                            <!--                            <th field="userid" width="1"> ID</th>-->
                            <th field="username" width="1"> Name</th>
                            <th field="useraddress" width="1"> Address</th>
                            <th field="userage" width="1"> Age</th>
                            <th field="gendertypeid" width="1">Gender</th>
                            <th field="phno" width="1">Phone</th>
                            <th field="emailid" width="1">Email</th>
                        </tr>
                    </thead>
                </table>
                <div id="toolbar">
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">New User</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">Edit User</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">Remove User</a>
                </div>
                <div id="dlg" class="easyui-dialog" style="width:500px;height:500px;padding:50px 80px"
                     closed="true" buttons="#dlg-buttons">
                    <div class="ftitle"></div>
                    <form id="fm" >

                        <div class="fitem">
                            <label>User Name:</label>
                            <input type="text" id="username" name="username"  class="easyui-textbox" required="true">
                        </div>
                        <div class="fitem">
                            <label>User Address:</label>
                            <input type="text" id="useraddress" name="useraddress"  class="easyui-textbox" required="true">
                        </div>
                        <div class="fitem">
                            <label>User Age:</label>
                            <input type="text" id="userage" name="userage"  class="easyui-numberbox" required="true">
                        </div>
                        <div class="fitem">
                            <label>Gender:</label>
                            <select type="text" class="easyui-combobox" id="gendertypeid" name="gendertypeid"  style="width:50%;height:26px;">

                                <option value="1">Male</option>
                                <option value="2">Female</option>
                            </select>

                        </div>
                        <div class="fitem">
                            <label>Phone:</label>
                            <input type="text" id="phno" name="phno" class="easyui-numberbox" required="true">
                        </div>


                        <div class="fitem">
                            <label>Email:</label>
                            <input type="text" id="emailid" name="emailid" class="easyui-textbox" required="true" validType="emailid">
                        </div>
                        <div id="dlg-buttons">
                            <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" id="addbutton" name="addbutton" onclick="addUser()" style="width:90px">Add</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" id="editbutton" name="editbutton" onclick="editUserDet()" style="width:90px">Edit</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" id="delbutton" name="delbutton" onclick="delUserDet()" style="width:90px">Delete</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()" style="width:90px">Cancel</a>

                        </div>
                    </form>
                </div>


            </div>

            <div title="Hub">
                <jsp:include page="/hubjsp.jsp">
                    <jsp:param value="1" name="actionType" />
                </jsp:include>
            </div>

            <div title="Meter">
                <jsp:include page="/meterjsp.jsp">
                    <jsp:param value="1" name="actionType" />
                </jsp:include>
            </div>
            <div title="Meter Reading">
                <jsp:include page="/Meterreading.jsp">
                    <jsp:param value="1" name="actionType" />
                </jsp:include>
            </div>


        </div>
        <script type="text/javascript">

            $(document).ready(function () {
                var URL = "SelectUserData";
                var parameters = "name=" + name + "&test=" + name;
                var method = "POST";
                var dataType = "json";
                $.ajax({
                    type: method,
                    data: parameters,
                    url: URL,
                    dataType: dataType,
                    cache: false,
                    success: function (data) {
                        tableData = {
                            total: data.rows.length,
                            rows: data.rows
                        };
                        $('#dg').datagrid({
                            data: tableData
                        });
                    },
                    async: false,
                    error: function (jqXHR, textStatus, errorThrown) {
                        throwAjaxErrorToUser(jqXHR, textStatus, errorThrown);
                    }
                });

            });




            function newUser() {
                $('#dlg').dialog('open').dialog('center').dialog('setTitle', 'New User');
                $('#fm').form('clear');
                $("#addbutton").show();
                $("#editbutton").hide();
                $("#delbutton").hide();

                // url='Testservlet';
            }
            function editUser() {
                var row = $('#dg').datagrid('getSelected');
                if (row) {
                    $('#dlg').dialog('open').dialog('center').dialog('setTitle', 'Edit User');
                    $('#fm').form('load', row);
                    var test = row.username;
                    $('#username').textbox('readonly');
                    $("#addbutton").hide();
                    $("#editbutton").show();
                    $("#delbutton").hide();
                }
            }

            function destroyUser() {
                var row = $('#dg').datagrid('getSelected');
                if (row) {
                    $.messager.confirm('Confirm', 'Are you sure you want to destroy this user?', function (r) {
                        if (r) {
                            var userName = row.username;
                            delUserDet(userName);
                        }
                    });
                }
            }
            function getdataAfterAdd() {
                var URL = "SelectUserData";
                var parameters = "name=" + name + "&test=" + name;
                var method = "POST";
                var dataType = "json";
                $.ajax({
                    type: method,
                    data: parameters,
                    url: URL,
                    dataType: dataType,
                    cache: false,
                    success: function (data) {
                        tableData = {
                            total: data.rows.length,
                            rows: data.rows
                        };
                        $('#dg').datagrid({
                            data: tableData
                        });
                    },
                    async: false,
                    error: function (jqXHR, textStatus, errorThrown) {
                        throwAjaxErrorToUser(jqXHR, textStatus, errorThrown);
                    }
                });
            }


            function addUser()
            {
                var username = $('#username').val();
                var useraddress = $('#useraddress').val();
                var userage = $('#userage').val();
                var gendertypeid = $('#gendertypeid').combobox('getValue');
                var phno = $('#phno').val();
                var emailid = $('#emailid').val();
                var URL = "AddUser";
                var parameters = "username=" + username + "&useraddress=" + useraddress + "&userage=" + userage + "&gendertypeid=" + gendertypeid + "&phno=" + phno + "&emailid=" + emailid;
                var method = "POST";
                var dataType = "json";
                $.ajax({
                    type: method,
                    data: parameters,
                    url: URL,
                    dataType: dataType,
                    cache: false,
                    success: function (data) {
                        getdataAfterAdd();
                        var dataMsg = data.message;
                        $('#dlg').dialog('close');
                        //  $.messager.alert('Warning', dataMsg);

                    },
                    async: false,
                    error: function (jqXHR, textStatus, errorThrown) {
                        throwAjaxErrorToUser(jqXHR, textStatus, errorThrown);
                    }
                });

            }

            function editUserDet() {
                var username = $('#username').val();
                var useraddress = $('#useraddress').val();
                var userage = $('#userage').val();
                var gendertypeid = $('#gendertypeid').combobox('getValue');
                var phno = $('#phno').val();
                var emailid = $('#emailid').val();
                var URL = "EditUser";
                var parameters = "username=" + username + "&useraddress=" + useraddress + "&userage=" + userage + "&gendertypeid=" + gendertypeid + "&phno=" + phno + "&emailid=" + emailid;
                var method = "POST";
                var dataType = "json";
                $.ajax({
                    type: method,
                    data: parameters,
                    url: URL,
                    dataType: dataType,
                    cache: false,
                    success: function (data) {
                        getdataAfterAdd();
                        var dataMsg = data.message;
                        $('#dlg').dialog('close');


                    },
                    async: false,
                    error: function (jqXHR, textStatus, errorThrown) {
                        throwAjaxErrorToUser(jqXHR, textStatus, errorThrown);
                    }
                });
            }

            function delUserDet(userName) {
                var username = userName;
                var URL = "DelUser";
                var parameters = "username=" + username;
                var method = "POST";
                var dataType = "json";
                $.ajax({
                    type: method,
                    data: parameters,
                    url: URL,
                    dataType: dataType,
                    cache: false,
                    success: function (data) {
                        getdataAfterAdd();
                        var dataMsg = data.message;
                        $('#dlg').dialog('close');


                    },
                    async: false,
                    error: function (jqXHR, textStatus, errorThrown) {
                        throwAjaxErrorToUser(jqXHR, textStatus, errorThrown);
                    }
                });
            }



        </script>

        <style type="text/css">
            #fm{
                margin:0;
                padding:10px 30px;
            }

            .ftitle{
                font-size:14px;
                font-weight:bold;
                padding:5px 0;
                margin-bottom:10px;
                border-bottom:1px solid #ccc;
            }
            .fitem{
                margin-bottom:5px;
            }
            .fitem label{
                display:inline-block;
                width:80px;
            }
            .fitem input{
                width:160px;
            }
        </style>      

    </body>
</html>