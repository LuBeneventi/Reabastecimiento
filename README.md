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


INSERT INTO `item_reabastecimiento`(`id`, `cantidad`, `id_producto`, `pedido_id`) VALUES (10, 50, 701,1001);
INSERT INTO `item_reabastecimiento`(`id`, `cantidad`, `id_producto`, `pedido_id`) VALUES (20, 30, 702,1002);
INSERT INTO `item_reabastecimiento`(`id`, `cantidad`, `id_producto`, `pedido_id`) VALUES (30, 100, 703,1003);
INSERT INTO `item_reabastecimiento`(`id`, `cantidad`, `id_producto`, `pedido_id`) VALUES (40, 20, 704,1004);



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
