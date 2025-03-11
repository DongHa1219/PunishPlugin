package org.soonsal.punishplugin.util;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.*;

public class Util {
    private Util() {
    }

    public static List<ItemStack> getItemListFromInventory(Inventory inventory) {
        return Arrays.stream(inventory.getContents())
                .filter(item -> item != null && !item.getType().isAir())
                .toList();
    }

    /**
     * 
     * @param map <제품, 확률> 쌍의 Map
     * @return 뽑힌 제품(Key)
     * @param <K> 제품
     */
    public static <K> K pickRandom(Map<K, Double> map) {
        int tmpRandom = Util.getRandomNumber(1, 100);
        double tmpRatePrev = 0;
        K product = null;

        for (Map.Entry<K, Double> entry : map.entrySet()) {
            double percentage = entry.getValue();
            double tmpRateNext = tmpRatePrev + percentage;

            if (tmpRandom <= tmpRateNext) {
                product = entry.getKey();
                break;
            }

            tmpRatePrev = tmpRateNext;
        }

        return product;
    }

    public static String getFullCommand(String[] args, int start) {
        List<String> commands = new ArrayList<>(Arrays.asList(args).subList(start, args.length));
        return String.join(" ", commands);
    }

    public static void spawnFireworks(Location location, Color... color){
        Location loc = location.clone().add(0, 1, 0);
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta meta = fw.getFireworkMeta();

        meta.setPower(1);
        meta.addEffect(
                FireworkEffect
                        .builder()
                        .flicker(true)
                        .trail(true)
                        .with(FireworkEffect.Type.BALL)
                        .withColor(color)
                        .build()
        );

        fw.setFireworkMeta(meta);
        fw.detonate();
    }

    public static boolean hasIndex(List<?> list, int index) {
        try {
            list.get(index);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public static boolean isMaterial(String material) {
        try {
            Material.valueOf(material);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * @param cost 원가
     * @param discountPercentage 감소시킬 퍼센트
     * @return 원가에서 해당 percentage 에 해당하는 값을 리턴합니다.
     * <P>EX) cost : 100 , percentage : 20 -> return : 20</P>
     */
    public static int discount(int cost, int discountPercentage) {
        double discountAmount = cost * ((double) discountPercentage / 100);
        return (int) discountAmount;
    }

    public static boolean checkPercentage(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("백분율은 0부터 100까지의 범위여야 합니다.");
        }

        Random random = new Random();
        double randomValue = random.nextDouble() * 100; // 0부터 100 사이의 랜덤 값 생성

        return randomValue < percentage;
    }

    public static String listToHtml(List<String> list) {
        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            builder.append(list.get(i));
            if (i != list.size() - 1) {
                builder.append("<br>");
            }
        }

        return builder.toString();
    }

    public static Integer randomRGB() {
        final Random random = new Random();

        int randomColor;
        do {
            randomColor = random.nextInt(16777216);
        } while (randomColor == 0x000000 || randomColor == 0xFFFFFF || randomColor == 0x00FF00 || randomColor == 0xFF0000);

        return randomColor;
    }

    /**
     *
     * @param cost 총 량
     * @param amount 총량중에 일부분
     * @return 해당 amount 가 몇 % 인지 리턴한다.
     */
    public static double percentage(double cost, double amount) {
        return (amount / cost) * 100;
    }

    public static boolean isSatisfied(long startMillis, int timeSecond) {
        return (System.currentTimeMillis() - startMillis) > (timeSecond * 1000L);
    }

    public static String getLocation(Chunk chunk) {
        return chunk.getX() + " : " + chunk.getZ();
    }

    public static OfflinePlayer getOfflinePlayer(UUID playerUUID) {
        final Player player = Bukkit.getPlayer(playerUUID);
        if (player != null) {
            return player;
        }

        return Bukkit.getOfflinePlayer(playerUUID);
    }

    public static String getPlayerName(UUID uuid) {
        return getOfflinePlayer(uuid).getName();
    }

    public static boolean containSpecialChar(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!Character.isLetter(c) && (c < 0xAC00 || c > 0xD7A3) && !Character.isWhitespace(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOnline(UUID uuid) {
        return Bukkit.getPlayer(uuid) != null;
    }

    public static boolean isOnline(String name) {
        return Bukkit.getPlayer(name) != null;
    }

    @SuppressWarnings("deprecation")
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> color(List<String> messages) {
        return messages.stream().map(Util::color).toList();
    }

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String performString(int start, int end, String[] args) {
        final StringBuilder builder = new StringBuilder();
        for (int i = start; i < end; i++) {
            builder.append(args[i]);
            if (i != (end - 1)) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }

    public static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * max + 1 - min) + min;
    }

    public static boolean hasInventorySlot(Inventory inventory, int requireSlot) {
        int check = 0;

        for (ItemStack itemStack : inventory.getStorageContents()) {
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                check++;
            }

            if (check >= requireSlot) {
                return true;
            }
        }

        return false;
    }

    public static boolean hasInventorySlot(Player player, int requireSlot) {
        return hasInventorySlot(player.getInventory(), requireSlot);
    }

    public static boolean contains(int[] values, int target) {
        return Arrays.stream(values)
                .filter(x -> x == target)
                .count() > 0;
    }

    public static boolean isInventoryFull(Player player) {
        return player.getInventory().firstEmpty() == -1;
    }
}

