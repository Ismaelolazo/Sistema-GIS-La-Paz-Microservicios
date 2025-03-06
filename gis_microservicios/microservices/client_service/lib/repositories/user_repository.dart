import 'package:postgres/postgres.dart';
import '../models/user.dart';

class UserRepository {
  late PostgreSQLConnection _connection;

  UserRepository() {
    _initDatabase();
  }

  Future<void> _initDatabase() async {
  try {
    _connection = PostgreSQLConnection(
      'localhost',
      5432,
      'gis', // You may want to rename this database
      username: 'postgres',
      password: '133133' // Replace with your PostgreSQL password
    );

    print('Connecting to PostgreSQL...');
    await _connection.open();
    print('Connected successfully!');
    
    // Create table if it doesn't exist
    await _createTable();
    print('Table setup complete');
  } catch (e) {
    print('Database connection error: $e');
    rethrow;
  }
}

Future<void> _createTable() async {
  await _connection.execute('''
    CREATE TABLE IF NOT EXISTS Usuarios (
      ID_usuario int NOT NULL,
      Nombre varchar(255) NULL,
      Correo varchar(255) NULL,
      Contra varchar(255) NULL,
      CONSTRAINT Usuarios_pk PRIMARY KEY (ID_usuario)
    )
  ''');
  
  // Check if constraint exists (to avoid duplicate constraint error)
  final constraintExists = await _connection.query('''
    SELECT 1 FROM pg_constraint WHERE conname = 'ak_0'
  ''');
  
  if (constraintExists.isEmpty) {
    await _connection.execute('''
      ALTER TABLE Usuarios ADD CONSTRAINT AK_0 UNIQUE (Correo)
    ''');
  }
}

  Future<List<User>> getAll() async {
    final results = await _connection.query('SELECT * FROM Usuarios');
    return results.map((row) {
      return User(
        id: row[0],
        nombre: row[1],
        correo: row[2],
        contra: row[3],
        createdAt: DateTime.now(), // These aren't in your schema but keeping for tracking
        updatedAt: null,
      );
    }).toList();
  }

  Future<User?> getById(int id) async {
    final results = await _connection.query(
      'SELECT * FROM Usuarios WHERE ID_usuario = @id',
      substitutionValues: {'id': id},
    );

    if (results.isEmpty) return null;

    final row = results.first;
    return User(
      id: row[0],
      nombre: row[1],
      correo: row[2],
      contra: row[3],
    );
  }

  Future<User> create(User user) async {
    // Get the next available ID or use a sequence if your DB has one
    final idResults = await _connection.query('SELECT COALESCE(MAX(ID_usuario), 0) + 1 FROM Usuarios');
    final id = idResults.first[0] as int;
    
    await _connection.execute(
      '''
      INSERT INTO Usuarios (ID_usuario, Nombre, Correo, Contra)
      VALUES (@id, @nombre, @correo, @contra)
      ''',
      substitutionValues: {
        'id': id,
        'nombre': user.nombre,
        'correo': user.correo,
        'contra': user.contra,
      },
    );

    return user.copyWith(id: id);
  }

  Future<User?> update(int id, User user) async {
    final results = await _connection.execute(
      '''
      UPDATE Usuarios
      SET Nombre = @nombre, Correo = @correo, Contra = @contra
      WHERE ID_usuario = @id
      ''',
      substitutionValues: {
        'id': id,
        'nombre': user.nombre,
        'correo': user.correo,
        'contra': user.contra,
      },
    );

    if (results == 0) return null;
    
    return user.copyWith(id: id);
  }

  Future<bool> delete(int id) async {
    final results = await _connection.execute(
      'DELETE FROM Usuarios WHERE ID_usuario = @id',
      substitutionValues: {'id': id},
    );

    return results > 0;
  }
}