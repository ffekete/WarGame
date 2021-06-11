package com.mygdx.wargame.battle.map.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.common.collect.ImmutableMap;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.screen.IsoUtils;
import com.mygdx.wargame.config.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.badlogic.gdx.graphics.g2d.Batch.*;

public class IsometricTiledMapRendererWithSprites extends IsometricTiledMapRenderer {

    private List<Actor> objects;
    private int drawSpritesAfterLayer = 4;
    private int currentLayer = 0;

    private Vector2 topRight = new Vector2();
    private Vector2 bottomLeft = new Vector2();
    private Vector2 topLeft = new Vector2();
    private Vector2 bottomRight = new Vector2();
    private Color defaultColor = null;


    private class Pair {
        public Texture texture;
        public float[] vertices;

        public Pair(Texture texture, float[] vertices) {
            this.texture = texture;
            this.vertices = vertices;
        }
    }

    private Pair[][][] tilesToRender = new Pair[BattleMap.WIDTH][BattleMap.HEIGHT][6];

    public IsometricTiledMapRendererWithSprites(TiledMap map) {
        super(map);
        init();
        objects = new ArrayList<>();

    }

    public void addObject(Actor object) {
        objects.add(object);
    }

    public void removeObject(Actor object) {
        objects.remove(object);
    }

    public void removeAll(Class clazz) {
        objects.removeAll(objects.stream().filter(object -> object.getClass().isAssignableFrom(clazz))
                .collect(Collectors.toList()));
    }

    @Override
    public void render() {

        for (int i = 0; i < BattleMap.WIDTH; i++) {
            for (int j = 0; j < BattleMap.HEIGHT; j++) {
                for (int k = 0; k < 6; k++) {
                    tilesToRender[i][j][k] = null;
                }
            }
        }

        currentLayer = 0;
        for (MapLayer layer : map.getLayers()) {

            if (layer.getName().equals("directionLayer") && !Config.showDirectionMarkers) {
                layer.setVisible(false);
            }

            if (layer.getName().equals("movementMarkersLayer") && !Config.showMovementMarkers) {
                layer.setVisible(false);
            }

            if (layer.getName().equals("rangeMarkersLayer") && !Config.showRangeMarkers) {
                layer.setVisible(false);
            }

            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer) layer);
                    currentLayer++;
                } else {
                    if (defaultColor != null)
                        batch.setColor(defaultColor);

                    for (MapObject object : layer.getObjects()) {
                        renderObject(object);
                    }
                }
            } else {
                currentLayer++;
            }
        }

        beginRender();

        renderAllInOrder();

        endRender();

    }

    private Matrix4 isoTransform;
    private Matrix4 invIsotransform;
    private Vector3 screenPos = new Vector3();

    public IsometricTiledMapRendererWithSprites(TiledMap map, Batch batch) {
        super(map, batch);
        init();
    }

    public IsometricTiledMapRendererWithSprites(TiledMap map, float unitScale) {
        super(map, unitScale);
        init();
    }

    public IsometricTiledMapRendererWithSprites(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
        init();
    }

    private void init() {
        // create the isometric transform
        isoTransform = new Matrix4();
        isoTransform.idt();

        // isoTransform.translate(0, 32, 0);
        isoTransform.scale((float) (Math.sqrt(2.0) / 2.0), (float) (Math.sqrt(2.0) / 4.0), 1.0f);
        isoTransform.rotate(0.0f, 0.0f, 1.0f, -45);

        // ... and the inverse matrix
        invIsotransform = new Matrix4(isoTransform);
        invIsotransform.inv();
    }

    private Vector3 translateScreenToIso(Vector2 vec) {
        screenPos.set(vec.x, vec.y, 0);
        screenPos.mul(invIsotransform);

        return screenPos;
    }

    @Override
    public void renderTileLayer(TiledMapTileLayer layer) {
        final Color batchColor = batch.getColor();
        final float color = Color.toFloatBits(batchColor.r, batchColor.g, batchColor.b, batchColor.a * layer.getOpacity());

        float tileWidth = layer.getTileWidth() * unitScale;
        float tileHeight = layer.getTileHeight() * unitScale / 2f;

        final float layerOffsetX = layer.getRenderOffsetX() * unitScale;
        // offset in tiled is y down, so we flip it
        final float layerOffsetY = -layer.getRenderOffsetY() * unitScale;

        float halfTileWidth = tileWidth * 0.5f;
        float halfTileHeight = tileHeight * 0.5f;

        // setting up the screen points
        // COL1
        topRight.set(viewBounds.x + viewBounds.width - layerOffsetX, viewBounds.y - layerOffsetY);
        // COL2
        bottomLeft.set(viewBounds.x - layerOffsetX, viewBounds.y + viewBounds.height - layerOffsetY);
        // ROW1
        topLeft.set(viewBounds.x - layerOffsetX, viewBounds.y - layerOffsetY);
        // ROW2
        bottomRight.set(viewBounds.x + viewBounds.width - layerOffsetX, viewBounds.y + viewBounds.height - layerOffsetY);

        // transforming screen coordinates to iso coordinates
        int row1 = (int) (translateScreenToIso(topLeft).y / tileWidth) - 2;
        int row2 = (int) (translateScreenToIso(bottomRight).y / tileWidth) + 2;

        int col1 = (int) (translateScreenToIso(bottomLeft).x / tileWidth) - 2;
        int col2 = (int) (translateScreenToIso(topRight).x / tileWidth) + 2;

        for (int row = row2; row >= row1; row--) {
            for (int col = col1; col <= col2; col++) {
                float x = (col * halfTileWidth) + (row * halfTileWidth);
                float y = (row * halfTileHeight) - (col * halfTileHeight) - 32;

                final TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                if (cell == null) continue;
                final TiledMapTile tile = cell.getTile();

                if (tile != null) {
                    final boolean flipX = cell.getFlipHorizontally();
                    final boolean flipY = cell.getFlipVertically();
                    final int rotations = cell.getRotation();

                    TextureRegion region = tile.getTextureRegion();

                    float x1 = x + tile.getOffsetX() * unitScale + layerOffsetX;
                    float y1 = y + tile.getOffsetY() * unitScale + layerOffsetY;
                    float x2 = x1 + region.getRegionWidth() * unitScale;
                    float y2 = y1 + region.getRegionHeight() * unitScale;

                    float u1 = region.getU();
                    float v1 = region.getV2();
                    float u2 = region.getU2();
                    float v2 = region.getV();

                    vertices[X1] = x1;
                    vertices[Y1] = y1;
                    vertices[C1] = color;
                    vertices[U1] = u1;
                    vertices[V1] = v1;

                    vertices[X2] = x1;
                    vertices[Y2] = y2;
                    vertices[C2] = color;
                    vertices[U2] = u1;
                    vertices[V2] = v2;

                    vertices[X3] = x2;
                    vertices[Y3] = y2;
                    vertices[C3] = color;
                    vertices[U3] = u2;
                    vertices[V3] = v2;

                    vertices[X4] = x2;
                    vertices[Y4] = y1;
                    vertices[C4] = color;
                    vertices[U4] = u2;
                    vertices[V4] = v1;

                    if (flipX) {
                        float temp = vertices[U1];
                        vertices[U1] = vertices[U3];
                        vertices[U3] = temp;
                        temp = vertices[U2];
                        vertices[U2] = vertices[U4];
                        vertices[U4] = temp;
                    }
                    if (flipY) {
                        float temp = vertices[V1];
                        vertices[V1] = vertices[V3];
                        vertices[V3] = temp;
                        temp = vertices[V2];
                        vertices[V2] = vertices[V4];
                        vertices[V4] = temp;
                    }
                    if (rotations != 0) {
                        switch (rotations) {
                            case TiledMapTileLayer.Cell.ROTATE_90: {
                                float tempV = vertices[V1];
                                vertices[V1] = vertices[V2];
                                vertices[V2] = vertices[V3];
                                vertices[V3] = vertices[V4];
                                vertices[V4] = tempV;

                                float tempU = vertices[U1];
                                vertices[U1] = vertices[U2];
                                vertices[U2] = vertices[U3];
                                vertices[U3] = vertices[U4];
                                vertices[U4] = tempU;
                                break;
                            }
                            case TiledMapTileLayer.Cell.ROTATE_180: {
                                float tempU = vertices[U1];
                                vertices[U1] = vertices[U3];
                                vertices[U3] = tempU;
                                tempU = vertices[U2];
                                vertices[U2] = vertices[U4];
                                vertices[U4] = tempU;
                                float tempV = vertices[V1];
                                vertices[V1] = vertices[V3];
                                vertices[V3] = tempV;
                                tempV = vertices[V2];
                                vertices[V2] = vertices[V4];
                                vertices[V4] = tempV;
                                break;
                            }
                            case TiledMapTileLayer.Cell.ROTATE_270: {
                                float tempV = vertices[V1];
                                vertices[V1] = vertices[V4];
                                vertices[V4] = vertices[V3];
                                vertices[V3] = vertices[V2];
                                vertices[V2] = tempV;

                                float tempU = vertices[U1];
                                vertices[U1] = vertices[U4];
                                vertices[U4] = vertices[U3];
                                vertices[U3] = vertices[U2];
                                vertices[U2] = tempU;
                                break;
                            }
                        }
                    }

                    float[] v = Arrays.copyOf(vertices, vertices.length);
                    // save for later rendering
                    tilesToRender[row][col][currentLayer] = new Pair(region.getTexture(), v);
                }
            }
        }
    }

    public void renderAllInOrder() {

        float tileWidth = IsoUtils.TILE_WIDTH * unitScale;
        float tileHeight = IsoUtils.TILE_HEIGHT * unitScale / 2f;

        int row1 = (int) (translateScreenToIso(topLeft).y / tileWidth) - 2;
        int row2 = (int) (translateScreenToIso(bottomRight).y / tileWidth) + 2;

        int col1 = (int) (translateScreenToIso(bottomLeft).x / tileWidth) - 2;
        int col2 = (int) (translateScreenToIso(topRight).x / tileWidth) + 2;

        ImmutableMap<Integer, String> layers = ImmutableMap.<Integer, String>builder()
                .put(0, "groundLayer")
                .put(1, "movementMarkersLayer")
                .put(2, "rangeMarkersLayer")
                .put(3, "directionLayer")
                .put(4, "pathLayer")
                .build();

        // ground
        for (int row = row2; row >= row1; row--) {
            for (int col = col1; col <= col2; col++) {
                for (Map.Entry<Integer, String> entry : layers.entrySet()) {

                    final TiledMapTileLayer.Cell cell = ((TiledMapTileLayer) map.getLayers().get(entry.getValue())).getCell(col, row);
                    if (cell != null) {
                        final TiledMapTile tile = cell.getTile();

                        if (tile != null && tilesToRender[row][col][entry.getKey()] != null) {
                            batch.draw(tilesToRender[row][col][entry.getKey()].texture, tilesToRender[row][col][entry.getKey()].vertices, 0, NUM_VERTICES);
                        }
                    }
                }
            }
        }

        // objects above ground
        for (int row = row2; row >= row1; row--) {
            for (int col = col1; col <= col2; col++) {
                for (Actor object : objects)
                    if ((int) object.getX() == col && (int) object.getY() == row)
                        object.draw(this.getBatch(), 1f);
            }
        }

        // layers above objects
        layers = ImmutableMap.<Integer, String>builder()
                .put(0, "foliageLayer")
                .build();

        for (int row = row2; row >= row1; row--) {
            for (int col = col1; col <= col2; col++) {
                for (Map.Entry<Integer, String> entry : layers.entrySet()) {

                    final TiledMapTileLayer.Cell cell = ((TiledMapTileLayer) map.getLayers().get(entry.getValue())).getCell(col, row);
                    if (cell != null) {
                        final TiledMapTile tile = cell.getTile();

                        if (tile != null && tilesToRender[row][col][entry.getKey()] != null) {
                            batch.draw(tilesToRender[row][col][entry.getKey()].texture, tilesToRender[row][col][entry.getKey()].vertices, 0, NUM_VERTICES);
                        }
                    }
                }
            }
        }
    }

    public List<Actor> getObjects() {
        objects.sort(new Comparator<Actor>() {
            @Override
            public int compare(Actor o1, Actor o2) {
                return Integer.compare((int) o2.getY(), (int) o1.getY());
            }
        });
        return objects;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }
}