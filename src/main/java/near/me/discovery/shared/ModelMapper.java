package near.me.discovery.shared;

public class ModelMapper {
    private static org.modelmapper.ModelMapper mapper = new org.modelmapper.ModelMapper();

    public static <T> T map(Object from, Class<T> to) {
        return mapper.map(from, to);
    }
}
