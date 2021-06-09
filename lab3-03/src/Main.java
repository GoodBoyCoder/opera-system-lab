import entity.Address;
import entity.Section;
import handler.AddressTranslation;
import handler.FIFOPageHandler;
import handler.FIFOSectionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GoodBoy
 * @date 2021-06-08
 */
public class Main {
    public static void main(String[] args) {
        //初始化段
        List<Section> initSection = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            initSection.add(new Section((long) i + 20));

        }

        Address address = new Address(1, 30);
        Address address1 = new Address(2,5550);
        Address address2 = new Address(2, 1230);
        Address address3 = new Address(2, 9210);
        AddressTranslation addressTranslation = new AddressTranslation(initSection, new FIFOPageHandler(), new FIFOSectionHandler());
        System.out.println("转换后的物理地址：" + addressTranslation.translation(address));
        System.out.println("转换后的物理地址：" + addressTranslation.translation(address1));
        System.out.println("转换后的物理地址：" + addressTranslation.translation(address2));
        System.out.println("转换后的物理地址：" + addressTranslation.translation(address3));
    }
}
