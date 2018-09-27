<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">


    </head>
    <body>
        <div style="margin:20px 0;">

        </div>
        <div title="Hub">
            <table id="hubtable" class= "easyui-datagrid"
                   url="SelectHubData"
                   hubtoolbar="#hubtoolbar" pagination="true"
                   rownumbers="true" fitColumns="true" singleSelect="true" idField="hubname">
                <thead>
                    <tr>
                        <!--                            <th field="hubid" width="1"> ID</th>-->
                        <th field="hubname" width="1"> Name</th>
                        <th field="hubtypeid" width="1"> Type ID  </th>
                        <th field="hubIP" width="1"> HUB IP</th>
                        <th field="hubdescription" width="1">Description</th>
                        <th field="userid" width="1">UserID</th>
                        <th field="hublatitude" width="1">Latitude</th>
                        <th field="hublongitude" width="1">Longitude</th>
                        <th field="hublocation" width="1">location</th>
                        <th field="hublandmark" width="1">landmark</th>

                    </tr>
                </thead>
            </table>
            <div id="hubtoolbar">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newHub()">Add</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editHub()">Edit</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyHub()">Delete</a>
            </div>
            <div id="hubdialog" class="easyui-dialog" style="width:500px;height:500px;padding:50px 80px"
                 closed="true" buttons="#hubdialog-buttons">
                <div class="htitle"></div>
                <form id="hm" >
                    <div class="hitem" >
                        <label>Hub Name:</label>
                        <input type="text" id="hubname" name="hubname"  class="easyui-textbox"  required="true">
                    </div>
                    <div class="hitem">
                        <label>Hub Type ID:</label>
                        <select class="easyui-combobox" id="hubtypeid" name="hubtypeid" option="multiple:false" style="width:50%;height:26px;" onchange="changeFunc();">

                            <option value="1">Serial</option>
                            <option value="2">UDP</option>
                        </select>

                    </div>
                    <div class="hitem">
                        <label>Hub IP:</label>
                        <input type="text" id="hubIP" name="hubIP"  class="easyui-numberbox"  required="true">
                    </div>
                    <div class="hitem">
                        <label>Hub Description:</label>
                        <input type="text" id="hubdescription" name="hubdescription"  class="easyui-textbox" required="true">
                    </div>

                    <div class="hitem">
                        <label class="label-top">User ID:</label>
                        <input id="userid" type ="text" class="easyui-combobox" name="userid" style="width:50%;" required/>
                    </div>
                    <div class="hitem">
                        <label class="label-top">Latitude:</label>
                        <input class="easyui-numberbox" id="hublatitude" name="hublatitude" precision="2" value="" required="true">

                    </div>
                    <div class="hitem">
                        <label class="label-top">Longitude</label>
                        <input class="easyui-numberbox" id="hublongitude" name="hublongitude" precision="2" value=""  required="true">
                    </div>
                    <div class="hitem">
                        <label>Location</label>
                        <input type="text" id="hublocation" name="hublocation" class="easyui-textbox" required="true">
                    </div>

                    <div class="hitem">
                        <label>Landmark:</label>
                        <input type="text" id="hublandmark" name="hublandmark" class="easyui-textbox"  required="true">
                    </div>
                    <div id="hubdialog-buttons">
                        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" id="addhubbutton" name="addhubbutton" onclick="addHub()" style="width:90px">Add</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" id="edithubbutton" name="edithubbutton" onclick="editHubDet()" style="width:90px">Edit</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" id="delhubbutton" name="delhubbutton" onclick="delHubDet()" style="width:90px">Delete</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:90px">Cancel</a>

                    </div>
                </form>
            </div>
        </div>
        <script type="text/javascript">

            $(document).ready(function () {
                var name = "";
                var URL = "SelectHubData";
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
                            rows: data.rows};
                        console.log("The Data Value : " + data.rows);
                        $('#hubtable').datagrid({
                            data: tableData
                        });
                    },
                    async: false,
                    error: function (jqXHR, textStatus, errorThrown) {
                        throwAjaxErrorToHub(jqXHR, textStatus, errorThrown);
                    }
                });
                userCombo();
            });


            function userCombo() {

                var name = "";
                var URL = "UserDetails";
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
                        var comboData = data.rows;
                        console.log(data.rows);

                        $('#userid').combobox({
                            data: comboData,
                            valueField: 'value',
                            textField: 'text',
                            cache: false,
                            required: true,
                            multiple: false,
                            editable: false,
                            onLoadSuccess: function () {
                                var getGroup = $('#userid').combobox('getValue');
                                var val = $(this).combobox("getData");
                                $.each(val, function (i, obj) {
                                    console.log("Text " + obj.text + "Value" + obj.value);
                                    if (getGroup === obj.text) {
                                        $('#userid').combobox('setValue', obj.value);
                                    }
                                });
                            }

                        });
                    },
                    async: false,
                    error: function (jqXHR, textStatus, errorThrown) {
                        throwAjaxErrorToHub(jqXHR, textStatus, errorThrown);
                    }
                });

            }

            function changeFunc() {
                var selectBox = document.getElementById("hubtypeid");
                var selectedValue = selectBox.options[selectBox.selectedIndex].value;
                alert(selectedValue);


//                var typeValue = $('#hubtypeid').combobox('getValue');
//                alert(typeValue);
//                if (typeValue === 1) {
//                    $('#hubIP').textbox('readonly');
//                }

            }



            function newHub() {
                $('#hubdialog').dialog('open').dialog('center').dialog('setTitle', 'New Hub');
                $('#hm').form('clear');
                $("#addhubbutton").show();
                $("#edithubbutton").hide();
                $("#delhubbutton").hide();

//                if ($('#hubtypeid').length) {
//                    checkSerial();
//                }

                // url='Testservlet';
            }
            function editHub() {
                var row = $('#hubtable').datagrid('getSelected');
                if (row) {
                    $('#hubdialog').dialog('open').dialog('center').dialog('setTitle', 'Edit Hub');
                    $('#hm').form('load', row);
                    $('#hubname').textbox('readonly');
                    $("#addhubbutton").hide();
                    $("#edithubbutton").show();
                    $("#delhubbutton").hide();
                }
            }

            function destroyHub() {
                var row = $('#hubtable').datagrid('getSelected');
                if (row) {
                    $.messager.confirm('Confirm', 'Are you sure you want to destroy this hub?', function (r) {
                        if (r) {
                            var hubName = row.hubname;
                            delHubDet(hubName);
                        }
                    });
                }
            }
            function getdataHubAfterAdd() {
                var URL = "SelectHubData";
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
                        $('#hubtable').datagrid({
                            data: tableData
                        });
                    },
                    async: false,
                    error: function (jqXHR, textStatus, errorThrown) {
                        throwAjaxErrorToHub(jqXHR, textStatus, errorThrown);
                    }
                });
            }


            function addHub()
            {
                var hubname = $('#hubname').val();
                var hubtypeid = $('#hubtypeid').combobox('getValue');
                var hubIP = $('#hubIP').val();
                var hubdescription = $('#hubdescription').val();
                var userid = $('#userid').combobox('getValue');
                var hublatitude = $('#hublatitude').val();
                var hublongitude = $('#hublongitude').val();
                var hublocation = $('#hublocation').val();
                var hublandmark = $('#hublandmark').val();
                var URL = "AddHub";
                var parameters = "hubname=" + hubname + "&hubtypeid=" + hubtypeid + "&hubIP=" + hubIP + "&hubdescription=" + hubdescription + "&userid=" + userid + "&hublatitude=" + hublatitude + "&hublongitude=" + hublongitude + "&hublocation=" + hublocation + "&hublandmark=" + hublandmark;
                var method = "POST";
                var dataType = "json";
                $.ajax({
                    type: method,
                    data: parameters,
                    url: URL,
                    dataType: dataType,
                    cache: false,
                    success: function (data) {
                        getdataHubAfterAdd();
                        var dataMsg = data.message;
                        $('#hubdialog').dialog('close');
                        //  $.messager.alert('Warning', dataMsg);

                    },
                    async: false,
                    error: function (jqXHR, textStatus, errorThrown) {
                        throwAjaxErrorToHub(jqXHR, textStatus, errorThrown);
                    }
                });

            }

            function editHubDet() {
                var hubname = $('#hubname').val();
                var hubtypeid = $('#hubtypeid').combobox('getValue');
                var hubIP = $('#hubIP').val();
                var hubdescription = $('#hubdescription').val();
                var userid = $('#userid').combobox('getValue');
                var hublatitude = $('#hublatitude').val();
                var hublongitude = $('#hublongitude').val();
                var hublocation = $('#hublocation').val();
                var hublandmark = $('#hublandmark').val();
                var URL = "EditHub";
                var parameters = "hubname=" + hubname + "&hubtypeid=" + hubtypeid + "&hubIP=" + hubIP + "&hubdescription=" + hubdescription + "&userid=" + userid + "&hublatitude=" + hublatitude + "&hublongitude=" + hublongitude + "&hublocation=" + hublocation + "&hublandmark=" + hublandmark;
                var method = "POST";
                var dataType = "json";
                $.ajax({
                    type: method,
                    data: parameters,
                    url: URL,
                    dataType: dataType,
                    cache: false,
                    success: function (data) {
                        getdataHubAfterAdd();
                        var dataMsg = data.message;
                        $('#hubdialog').dialog('close');


                    },
                    async: false,
                    error: function (jqXHR, textStatus, errorThrown) {
                        throwAjaxErrorToHub(jqXHR, textStatus, errorThrown);
                    }
                });
            }

            function delHubDet(hubName) {
                var hubname = hubName;
                var URL = "DelHub";
                var parameters = "hubname=" + hubname;
                var method = "POST";
                var dataType = "json";
                $.ajax({type: method,
                    data: parameters,
                    url: URL,
                    dataType: dataType,
                    cache: false,
                    success: function (data) {
                        getdataHubAfterAdd();
                        var dataMsg = data.message;
                        $('#hubdialog').dialog('close');


                    },
                    async: false,
                    error: function (jqXHR, textStatus, errorThrown) {
                        throwAjaxErrorToHub(jqXHR, textStatus, errorThrown);
                    }
                });
            }


        </script>

        <style type="text/css">
            #hm{
                margin:0;
                padding:10px 30px;
            }

            .htitle{
                font-size:14px;
                font-weight:bold;
                padding:5px 0;
                margin-bottom:10px;
                border-bottom:1px solid #ccc;
            }
            .hitem{
                margin-bottom:5px;
            }
            .hitem label{
                display:inline-block;
                width:80px;
            }
            .hitem input{
                width:160px;
            }
        </style>      

    </body>
</html>