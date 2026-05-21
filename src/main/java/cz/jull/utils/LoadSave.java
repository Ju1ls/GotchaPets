package cz.jull.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static final String MENU_BUTTONS = "menu_buttons.png";
    public static final String MENU_BACKGROUND = "menu_background.png";

    public static final String CREDITS_TITLE = "credits_title.png";
    public static final String CREDITS_FOREGROUND = "foreground.png";

    public static final String BACK_BUTTON = "back_button.png";
    public static final String GAME_BUTTONS = "game_buttons.png";
    public static final String PET_HEALTH_STATES = "pet_health_states.png";
    public static final String CURRENCY = "coin_background.png";

    public static final String DEFAULT_PET_SPRITES = "default_pet_sprites.png";
    public static final String PET_BED = "pet_bed.png";

    public static BufferedImage getSpriteAtlas(String fileName) {
        BufferedImage img = null;
        try (InputStream is = LoadSave.class.getResourceAsStream("/" + fileName)) {
            try {
                assert is != null;
                img = ImageIO.read(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}
