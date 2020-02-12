package com.mygdx.wargame.battle.map;

public class MapUtils {

    public static int bitmask4bitForTile(NodeGraph map, int x, int y, TileOverlayType tile, int skip) {
        int mask = 0;

        if (y + skip < map.getHeight() && map.getNodeWeb()[x][y + skip].getGroundOverlay() != null && map.getNodeWeb()[x][y + skip].getGroundOverlay().getTileOverlayType().equals(tile)) {
            mask += 1;
        }

        if (x + skip < map.getWidth() && map.getNodeWeb()[x + skip][y].getGroundOverlay() != null && map.getNodeWeb()[x + skip][y].getGroundOverlay().getTileOverlayType().equals(tile)) {
            mask += 2;
        }

        if (y - skip >= 0 && map.getNodeWeb()[x][y - skip].getGroundOverlay() != null && map.getNodeWeb()[x][y - skip].getGroundOverlay().getTileOverlayType().equals(tile)) {
            mask += 4;
        }

        if (x - skip >= 0 && map.getNodeWeb()[x - skip][y].getGroundOverlay() != null && map.getNodeWeb()[x - skip][y].getGroundOverlay().getTileOverlayType().equals(tile)) {
            mask += 8;
        }

        return mask;
    }

}
