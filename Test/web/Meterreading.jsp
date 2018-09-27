<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">

    </head>
    <body>
        <div title="Meter Reading">
            <table id="meterReading" class="easyui-datagrid"
                   rownumbers="true" fitColumns="true" singleSelect="true">
                <thead>
                    <tr>
                        <th field="meterreadingrecid" width="1">Id</th>
                        <th field="meterrecid" width="1">Meter</th>
                        <th field="voltage" width="1">Voltage</th>
                        <th field="current" width="1">Current</th>
                        <th field="power" width="1">Power</th>
                        <th field="datetime" width="1">Datetime</th>
                    </tr>
                </thead>
            </table>
        </div>
        <script type="text/javascript">
            $(document).ready(function () {
                var URL = "Meterreading";
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
                        $('#meterReading').datagrid({
                            data: tableData
                        });
                    },
                    async: false,
                    error: function (jqXHR, textStatus, errorThrown) {
                        throwAjaxErrorToUser(jqXHR, textStatus, errorThrown);
                    }
                });
                 
            });


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