import javafx.scene.image.Image;

public class MapData {
    public static final int TYPE_WALL   = 1;
    public static final int TYPE_NONE   = 0;
    public static final int TYPE_ITEM = 2;
    public static final int TYPE_STEP = 3;
    public Image[] mapImage;
    private int[] map;
    private int[] map2image;
    private int width;
    private int height;
    private int item_count = 3;

    MapData(int x, int y){ 
        mapImage = new Image[4];
        mapImage[TYPE_NONE] = new Image("SPACE.png");
        mapImage[TYPE_WALL] = new Image("WALL.png");
        mapImage[TYPE_ITEM] = new Image("mig.jpg");
        mapImage[TYPE_STEP] = new Image("SPACE.png");
        //mapImage[TYPE_STEP] = new Image("step.png");
        width  = x;
        height = y;
        map = new int[y*x];
        fillMap(MapData.TYPE_WALL);
        digMap(1, 3);
        map[toIndex(19,13)] = TYPE_STEP;
        setItem();
        /*マップ生成確認のためと思われる
          printMap();*/
    }
    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public int toIndex(int x, int y){
        return x + y * width;
    }
    //指定された座標がマップ外なら−1を返す
    public int getMap(int x, int y) {
        if (x < 0 || width <= x || y < 0 || height <= y) {
            return -1;
        }
        return map[toIndex(x,y)];
    }
    //マップイメージ取得
    public Image getImage(int x, int y) {
        return mapImage[getMap(x,y)];
    }

    public void setMap(int x, int y, int type){
        //画面端なら掘らない
        if (x < 1 || width <= x-1 || y < 1 || height <= y-1) {
            return;
        }
        map[toIndex(x,y)] = type;
    }
    //アイテムの後処理
    public void getItem(int x,int y){
        map[toIndex(x,y)] = TYPE_NONE;
    }

    //type(Wall)でマップをすべて埋める
    public void fillMap(int type){
        for (int y=0; y<height; y++){
            for (int x=0; x<width; x++){
                map[toIndex(x,y)] = type;
            }
        }
    }

    //Wallで埋まっていたところに道を作る
    public void digMap(int x, int y){
        setMap(x, y, MapData.TYPE_NONE);
        int[][] dl = {{0,1},{0,-1},{-1,0},{1,0}};
        int[] tmp;

        for (int i=0; i<dl.length; i++) {
            int r = (int)(Math.random()*dl.length);
            tmp = dl[i];
            dl[i] = dl[r];
            dl[r] = tmp;
        }

        for (int i=0; i<dl.length; i++){
            int dx = dl[i][0];
            int dy = dl[i][1];
            /*もしマップを取得してその場所が壁なら掘る
              マップ外が指定されたらgetMap()==-1となり,壁は掘られない*/
            if (getMap(x+dx*2, y+dy*2) == MapData.TYPE_WALL){
                setMap(x+dx, y+dy, MapData.TYPE_NONE);
                digMap(x+dx*2, y+dy*2);
                //穴を掘るついでにアイテムを追加
                if(Math.random() < 0.3 && item_count>0){
                    map[toIndex(x+dx,y+dy)] = MapData.TYPE_ITEM;
                    item_count--;
                }
            }

        }
    }
    public void setItem(){
    }

    public void printMap(){
        for (int y=0; y<height; y++){
            for (int x=0; x<width; x++){
                if (getMap(x,y) == MapData.TYPE_WALL){
                    System.out.print("++");
                }else{
                    System.out.print("  ");
                }
            }
            System.out.print("\n");
        }
    }
}

