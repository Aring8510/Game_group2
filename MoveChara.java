import javafx.scene.image.Image;

public class MoveChara {
    public static final int TYPE_DOWN  = 0;
    public static final int TYPE_LEFT  = 1;
    public static final int TYPE_RIGHT = 2;
    public static final int TYPE_UP    = 3;

    protected int posX;
    protected int posY;
    protected int charaDir;

    protected MapData mapData;

    private Image[] charaImage;
    private int count   = 0;
    private int diffx   = 1;
    private int itemCount;

    MoveChara(int startX, int startY, MapData mapData){
        this.mapData = mapData;
        charaImage = new Image[12];
        charaImage[0 * 3 + 0] = new Image("pic/nekod1.jpg");
        charaImage[0 * 3 + 1] = new Image("pic/nekod2.jpg");
        charaImage[0 * 3 + 2] = new Image("pic/nekod3.jpg");
        charaImage[1 * 3 + 0] = new Image("pic/nekol1.jpg");
        charaImage[1 * 3 + 1] = new Image("pic/nekol2.jpg");
        charaImage[1 * 3 + 2] = new Image("pic/nekol3.jpg");
        charaImage[2 * 3 + 0] = new Image("pic/nekor1.jpg");
        charaImage[2 * 3 + 1] = new Image("pic/nekor2.jpg");
        charaImage[2 * 3 + 2] = new Image("pic/nekor3.jpg");
        charaImage[3 * 3 + 0] = new Image("pic/nekou1.jpg");
        charaImage[3 * 3 + 1] = new Image("pic/nekou2.jpg");
        charaImage[3 * 3 + 2] = new Image("pic/nekou3.jpg");

        posX = startX;
        posY = startY;

        charaDir = TYPE_DOWN;
    }

    public int getIndex(){
        return charaDir * 3 + count;
    }
    public int getCharaDir(){
        return charaDir;
    }

    public void changeCount(){
        count = count + diffx;
        if (count > 2) {
            count = 1;
            diffx = -1;
        } else if (count < 0){
            count = 1;
            diffx = 1;
        }
    }

    public int getPosX(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }

    public void setCharaDir(int cd){
        if(cd == TYPE_RIGHT){
            switch(charaDir){
                case TYPE_UP:
                    charaDir = TYPE_RIGHT;
                    break;
                case TYPE_LEFT:
                    charaDir = TYPE_UP;
                    break;
                case TYPE_DOWN:
                    charaDir = TYPE_LEFT;
                    break;
                case TYPE_RIGHT:
                    charaDir = TYPE_DOWN;
                    break;
            }
        }else if(cd == TYPE_LEFT){
            switch(charaDir){
                case TYPE_UP:
                    charaDir = TYPE_LEFT;
                    break;
                case TYPE_LEFT:
                    charaDir = TYPE_DOWN;
                    break;
                case TYPE_DOWN:
                    charaDir = TYPE_RIGHT;
                    break;
                case TYPE_RIGHT:
                    charaDir = TYPE_UP;
                    break;
            }
        }
    }

    public boolean canMove(int dx, int dy){
        switch(mapData.getMap(posX+dx, posY+dy)){
            case MapData.TYPE_WALL:
                return false;
            case MapData.TYPE_NONE:
                return true;
            case MapData.TYPE_ITEM:
                //アイテムとぶつかった時の条件文
                mapData.getItem(posX+dx,posY+dy);
                itemCount++;
                return true;
            case MapData.TYPE_STEP:
                return true;
            default:
                return false;
        }
    }

    public boolean move(int dx, int dy){
        if (canMove(dx,dy)){
            posX += dx;
            posY += dy;
            return true;
        }
        return false;
    }

    public Image getImage(){
        changeCount();
        return charaImage[getIndex()];
    }

    public int getItemCount(){ return itemCount; }
}


