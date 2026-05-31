package cz.jull.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * A utility class housing all image resource path constants and a centralized method
 * for loading Image resources from the application's classpath.
 */
public class LoadSave {
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String HOME_BACKGROUND = "home_background.png";
    public static final String WATER_BACKGROUND = "water_background.png";

    public static final String CREDITS_TITLE = "credits_title.png";
    public static final String CREDITS_FOREGROUND = "foreground.png";

    public static final String MENU_BUTTONS = "menu_buttons.png";
    public static final String BACK_BUTTON = "back_button.png";
    public static final String GAME_BUTTONS = "game_buttons.png";
    public static final String GACHA_BUTTONS = "gacha_buttons.png";

    public static final String HOME_BANNER_GACHA = "home_gacha_banner.png";
    public static final String WATER_BANNER_GACHA = "water_gacha_banner.png";

    public static final String HOME_BOX = "home_box.png";
    public static final String HOME_BOX_CLOSED = "home_box_closed.png";
    public static final String HOME_BOX_OPEN = "home_box_open.png";

    public static final String WATER_BOX = "water_box.png";
    public static final String WATER_BOX_CLOSED = "water_box_closed.png";
    public static final String WATER_BOX_OPEN = "water_box_open.png";

    public static final String PET_LIST = "pet_list.png";
    public static final String PET_NOT_OWNED = "pet_not_owned.png";

    public static final String PET_HEALTH_STATES = "pet_health_states.png";
    public static final String CURRENCY = "coin_background.png";

    public static final String DEFAULT_PET_SPRITES = "default_pet_sprites.png";
    public static final String HAMSTER_RABBIT_SPRITES = "hamster_rabbit_sprites.png";
    public static final String PARROT_SPRITES = "parrot_sprites.png";
    public static final String JELLYFISH_SEAL_SPRITES = "jellyfish_seal_sprites.png";
    public static final String OCTOPUS_TURTLE_SPRITES = "octopus_turtle_sprites.png";
    public static final String SHARK_SPRITES = "shark_sprites.png";

    public static final String PET_BED = "pet_bed.png";

    /**
     * Loads a BufferedImage from the classpath.
     *
     * @param fileName The exact file name of the resource (e.g., "menu_background.png").
     * @return The loaded BufferedImage, or null if the file cannot be read.
     */
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
