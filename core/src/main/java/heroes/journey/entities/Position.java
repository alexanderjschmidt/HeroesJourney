package heroes.journey.entities;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.Getter;

@Getter
public class Position {

    private int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position setX(int x) {
        this.x = x;
        return this;
    }

    public Position setY(int y) {
        this.y = y;
        return this;
    }

    public Position setPos(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static Json.Serializer<Position> getJSONSerializer() {
        return new Json.Serializer<>() {
            @Override
            public void write(Json json, Position position, Class aClass) {
                json.writeObjectStart();
                json.writeValue("x", position.getX());
                json.writeValue("y", position.getY());
                json.writeObjectEnd();
            }

            @Override
            public Position read(Json json, JsonValue jsonValue, Class aClass) {
                int x = jsonValue.getInt("x", 0);
                int y = jsonValue.getInt("y", 0);
                return new Position(x, y);
            }
        };
    }

    public static Serializer<Position> getKryoSerializer() {
        return new Serializer<>() {
            @Override
            public void write(Kryo kryo, Output output, Position position) {
                output.writeInt(position.x);
                output.writeInt(position.y);
            }

            @Override
            public Position read(Kryo kryo, Input input, Class<Position> aClass) {
                int x = input.readInt();
                int y = input.readInt();
                return new Position(x, y);
            }
        };
    }

    public int distanceTo(Position other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        // return (int) Math.sqrt(dx * dx + dy * dy); // Euclidean
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y); // Manhattan
    }
}
