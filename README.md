#
##
###
INSERT INTO `reabastecimiento` (`id_reabastecimiento`, `estado`, `fecha_emision`, `id_proveedor`)
VALUES (1001, 'EN_PROCESO', '2025-05-20', 201);

INSERT INTO `reabastecimiento` (`id_reabastecimiento`, `estado`, `fecha_emision`, `id_proveedor`)
VALUES (1002, 'RECIBIDO', '2025-05-18', 202);

INSERT INTO `reabastecimiento` (`id_reabastecimiento`, `estado`, `fecha_emision`, `id_proveedor`)
VALUES (1003, 'ENVIADO', '2025-05-22', 203);

INSERT INTO `reabastecimiento` (`id_reabastecimiento`, `estado`, `fecha_emision`, `id_proveedor`)
VALUES (1004, 'CANCELADO', '2025-05-19', 204);

INSERT INTO `reabastecimiento` (`id_reabastecimiento`, `estado`, `fecha_emision`, `id_proveedor`)
VALUES (1005, 'EN_PROCESO', '2025-05-25', 205);


INSERT INTO `reabastecimiento_items` (`reabastecimiento_id_reabastecimiento`, `cantidad`, `id_producto`)
VALUES (1001, 50, 701);

INSERT INTO `reabastecimiento_items` (`reabastecimiento_id_reabastecimiento`, `cantidad`, `id_producto`)
VALUES (1002, 30, 702);

INSERT INTO `reabastecimiento_items` (`reabastecimiento_id_reabastecimiento`, `cantidad`, `id_producto`)
VALUES (1003, 100, 703);

INSERT INTO `reabastecimiento_items` (`reabastecimiento_id_reabastecimiento`, `cantidad`, `id_producto`)
VALUES (1004, 20, 704);

INSERT INTO `reabastecimiento_items` (`reabastecimiento_id_reabastecimiento`, `cantidad`, `id_producto`)
VALUES (1005, 75, 705);



{
"idProveedor": 1,
"items":[{
"idProducto": 12,
"cantidad": 80
},{
"idProducto": 78,
"cantidad": 150
}]
}
