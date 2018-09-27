<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
    </head>
    <body>

        <div style="margin:20px 0;">

        </div>
        <div title="Meter">
            <table id="meterDataTable" class= "easyui-datagrid"
                   url="SelectMeterData"
                   toolbar="#toolbars" pagination="true"
                   rownumbers="true" fitColumns="true" singleSelect="true" idField="metername">
                <thead>
                    <tr>
                        <th field="metername" width="1"> Name</th>
                        <th field="hubid" width="1"> HubID</th>
                        <th field="nodeid" width="1"> NodeID</th>
                        <th field="metertypeid" width="1">MetertypeId</th>
                        <th field="phasetypeid" width="1">PhasetypeId</th>
                        <th field="currenttypeid" width="1">CurrentTypeId</th>
                        <th field="meterparentid" width="1">MeterparentId</th>
                        <th field="meterlatitude" width="1">Meterlatitude</th>
                        <th field="meterlongitude" width="1">Meterlongitude</th>
                        <th field="meterlocation" width="1">Meterlocation</th>
                        <th field="meterlandmark" width="1">Meterlandmark</th>
                    </tr>
                </thead>
            </table>
            <div id="toolbars">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newMeter()">Add</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editMeter()">Edit</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyMeter()">Delete</a>
            </div>
            <div id="meterDataDai" class="easyui-dialog" style="width:500px;height:500px;padding:50px 80px"
                 closed="true" buttons="#dlg-buttonsMeter">
                <div class="ftitle"></div>
                <form id="meterDataForm" >

                    <div class="fitem">
                        <label>Meter Name:</label>
                        <input type="text" id="metername" name="metername"  class="easyui-textbox" required="true">
                    </div>
                    <div class="fitem">
                        <label>Hubid</label>
                        <input id="hubid" type ="text" class="easyui-combobox" name="hubid" style="width:50%;" required/>
                    </div>
                    <div class="fitem">
                        <label>Nodeid</label>
                        <input type="text" id="nodeid" name="nodeid"  class="easyui-numberbox" required="true">
                    </div>
                    <div class="fitem">
                        <label>MeterType:</label>
                        <select type="text" class="easyui-combobox" id="metertypeid" name="metertypeid"  style="width:50%;height:26px;">

                            <option value="1">Repeater</option>
                            <option value="2">Meter</option>
                        </select>

                    </div>
                    <div class="fitem">
                        <label>PhaseType:</label>
                        <select type="text" class="easyui-combobox" id="phasetypeid" name="phasetypeid"  style="width:50%;height:26px;">

                            <option value="1">Single</option>
                            <option value="2">3</option>
                        </select>

                    </div>
                    <div class="fitem">
                        <label>CurrentType:</label>
                        <select type="text" class="easyui-combobox" id="currenttypeid" name="currenttypeid"  style="width:50%;height:26px;">

                            <option value="1">AC</option>
                            <option value="2">DC</option>
                        </select>

                    </div>
                    <div class="fitem">
                        <label>MeterParentid:</label>
                        <select type="text" class="easyui-combobox" id="meterparentid" name="meterparentid"  style="width:50%;height:26px;">

                            <option value="0">Parent</option>
                            <option value="1">Child</option>
                        </select>

                    </div>
                    <div class="fitem">
                        <label>MeterLatitude:</label>
                        <input class="easyui-numberbox" precision="2" value="" id="meterlatitude" name="meterlatitude" required="true">
                    </div>


                    <div class="fitem">
                        <label>MeterLongitude:</label>
                        <input class="easyui-numberbox" precision="2" value="" id="meterlongitude" name="meterlongitude" required="true">
                    </div>

                    <div class="fitem">
                        <label>MeterLocation</label>
                        <input type="text" id="meterlocation" name="meterlocation" class="easyui-textbox" required="true">
                    </div>

                    <div class="fitem">
                        <label>MeterLandmark</label>
                        <input type="text" id="meterlandmark" name="meterlandmark" class="easyui-textbox" required="true">
                    </div>

                    <div id="dlg-buttonsMeter">
                        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" id="addbuttonmeter" name="addbuttonmeter" onclick="addMeter()" style="width:90px">Add</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" id="editbuttonmeter" name="editbuttonmeter" onclick="editMeterDet()" style="width:90px">Edit</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" id="delbuttonmeter" name="delbuttonmeter" onclick="delMeterDet()" style="width:90px">Delete</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:90px">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
        <script type="text/javascript">

            $(document).ready(function () {
                var URL = "SelectMeterData";
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
                        $('#meterDataTable').datagrid({
                            data: tableData
                        });
                    },
                    async: false,
                    error: function (jqXHR, textStatus, errorThrown) {
                        throwAjaxErrorToUser(jqXHR, textStatus, errorThrown);
                    }
                });

                hubCombo();

            });

            function hubCombo() {

                var name = "";
                var URL = "HubDetails";
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
                        $('#hubid').combobox({
                            data: comboData,
                            valueField: 'value',
                            textField: 'text',
                            cache: false,
                            required: true,
                            multiple: false,
                            editable: false,
                            onLoadSuccess: function () {
                                var getGroup = $('#hubid').combobox('getValue');
                                var val = $(this).combobox("getData");
                                $.each(val, function (i, obj) {
                                    console.log("Text " + obj.text + "Value" + obj.value);
                                    if (getGroup === obj.text) {
                                        $('#hubid').combobox('setValue', obj.value);
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

            function newMeter() {
                $('#meterDataDai').dialog('open').dialog('center').dialog('setTitle', 'New Meter');
                $('#meterDataForm').form('clear');
                $("#addbuttonmeter").show();
                $("#editbuttonmeter").hide();
                $("#delbuttonmeter").hide();
                $("#metername").prop('readonly', false);
                // url='Testservlet';
            }
            function editMeter() {
                var row = $('#meterDataTable').datagrid('getSelected');
                if (row) {
                    $('#meterDataDai').dialog('open').dialog('center').dialog('setTitle', 'Edit Meter');
                    $('#meterDataForm').form('load', row);
                    var test = row.metername;
                    $('#metername').textbox('readonly');
                    $("#addbuttonmeter").hide();
                    $("#editbuttonmeter").show();
                    $("#delbuttonmeter").hide();
                }
            }

            function destroyMeter() {
                var row = $('#meterDataTable').datagrid('getSelected');
                if (row) {
                    $.messager.confirm('Confirm', 'Are you sure you want to destroy this meter?', function (r) {
                        if (r) {
                            var meterName = row.metername;
                            delMeterDet(meterName);
                        }
                    });
                }
            }


            function getdataMeterAfterAdd() {
                var name = "";
                var URL = "SelectMeterData";
                var parameters = "name=" + name;
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
                        $('#meterDataTable').datagrid({
                            data: tableData
                        });
                    },
                    async: false,
                    error: function (jqXHR, textStatus, errorThrown) {
                        throwAjaxErrorToUser(jqXHR, textStatus, errorThrown);
                    }
                });
            }


            function addMeter()
            {
                var metername = $('#metername').val();
                var hubid = $('#hubid').combobox('getValue');
                var nodeid = $('#nodeid').val();
                var metertypeid = $('#metertypeid').combobox('getValue');
                var phasetypeid = $('#phasetypeid').combobox('getValue');
                var currenttypeid = $('#currenttypeid').combobox('getValue');
                var meterparentid = $('#meterparentid').combobox('getValue');
                var meterlatitude = $('#meterlatitude').val();
                var meterlongitude = $('#meterlongitude').val();
                var meterlocation = $('#meterlocation').val();
                var meterlandmark = $('#meterlandmark').val();

                var URL = "AddMeter";
                var parameters = "metername=" + metername + "&hubid=" + hubid + "&nodeid=" + nodeid + "&metertypeid=" + metertypeid + "&phasetypeid=" + phasetypeid + "&currenttypeid=" + currenttypeid + "&meterparentid=" + meterparentid + "&meterlatitude=" + meterlatitude + "&meterlongitude=" + meterlongitude + "&meterlocation=" + meterlocation + "&meterlandmark=" + meterlandmark;
                var method = "POST";
                var dataType = "json";
                $.ajax({
                    type: method,
                    data: parameters,
                    url: URL,
                    dataType: dataType,
                    cache: false,
                    success: function (data) {
                        getdataMeterAfterAdd();
                        var dataMsg = data.message;
                        $('#meterDataDai').dialog('close');
                        //  $.messager.alert('Warning', dataMsg);

                    },
                    async: false,
                    error: function (jqXHR, textStatus, errorThrown) {
                        throwAjaxErrorToUser(jqXHR, textStatus, errorThrown);
                    }
                });

            }

            function editMeterDet() {
                var metername = $('#metername').val();
                var hubid = $('#hubid').combobox('getValue');
                var nodeid = $('#nodeid').val();
                var metertypeid = $('#metertypeid').combobox('getValue');
                var phasetypeid = $('#phasetypeid').combobox('getValue');
                var currenttypeid = $('#currenttypeid').combobox('getValue');
                var meterparentid = $('#meterparentid').combobox('getValue');
                var meterlatitude = $('#meterlatitude').val();
                var meterlongitude = $('#meterlongitude').val();
                var meterlocation = $('#meterlocation').val();
                var meterlandmark = $('#meterlandmark').val();
                var URL = "EditMeter";
                var parameters = "metername=" + metername + "&hubid=" + hubid + "&nodeid=" + nodeid + "&metertypeid=" + metertypeid + "&phasetypeid=" + phasetypeid + "&currenttypeid=" + currenttypeid + "&meterparentid=" + meterparentid + "&meterlatitude=" + meterlatitude + "&meterlongitude=" + meterlongitude + "&meterlocation=" + meterlocation + "&meterlandmark=" + meterlandmark;
                var method = "POST";
                var dataType = "json";
                $.ajax({
                    type: method,
                    data: parameters,
                    url: URL,
                    dataType: dataType,
                    cache: false,
                    success: function (data) {
                        getdataMeterAfterAdd();
                        var dataMsg = data.message;
                        $('#meterDataDai').dialog('close');


                    },
                    async: false,
                    error: function (jqXHR, textStatus, errorThrown) {
                        throwAjaxErrorToUser(jqXHR, textStatus, errorThrown);
                    }
                });
            }




            function delMeterDet(meterName) {
                var metername = meterName;
                var URL = "DelMeter";
                var parameters = "metername=" + metername;
                var method = "POST";
                var dataType = "json";
                $.ajax({
                    type: method,
                    data: parameters,
                    url: URL,
                    dataType: dataType,
                    cache: false,
                    success: function (data) {
                        getdataMeterAfterAdd();
                        var dataMsg = data.message;
                        $('#meterDataDai').dialog('close');


                    },
                    async: false,
                    error: function (jqXHR, textStatus, errorThrown) {
                        throwAjaxErrorToUser(jqXHR, textStatus, errorThrown);
                    }
                });
            }



        </script>

        <style type="text/css">
            #meterDataForm{
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