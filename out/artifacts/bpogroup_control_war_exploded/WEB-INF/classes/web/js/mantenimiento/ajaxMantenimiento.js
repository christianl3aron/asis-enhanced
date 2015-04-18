/**
 * Created by Christianl3aron on 16/03/2015.
 */
$(document).ready(function () {

    $('#btnShow').click(function () {
        $('#container').html('');
        alert($("#sltPersonal").chosen().val());
        $('#img-loader').show();

        var worker = new Worker('js/mantenimiento/worker/WMMA.js');
        worker.postMessage({
            'act': 'showAsi',
            'ti': $('#timeIni').val(),
            'tf': $('#timeFin').val(),
            'cods': $("#sltPersonal").chosen().val().toString()
        });
        worker.addEventListener('message', function (e) {
            $('#container').html(e.data);
            $('#img-loader').hide();
        }, false);
    });


    $('#btnSave').click(function () {

        alert(ids);
        alert(vals);

        $.ajax({
            type: 'GET',
            url: 'MantenimientoServlet',
            data: {
                act: 'saveCha',
                ids: ids.toString(),
                vals: vals.toString()
            },
            success: function (dados) {
            }
        });

        ids = [];
        vals = [];

    });
});