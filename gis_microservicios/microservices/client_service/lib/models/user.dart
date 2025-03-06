class User {
  final int? id;
  final String? nombre;
  final String? correo;
  final String? contra;
  final DateTime createdAt;
  final DateTime? updatedAt;

  User({
    this.id,
    this.nombre,
    this.correo,
    this.contra,
    DateTime? createdAt,
    this.updatedAt,
  }) : createdAt = createdAt ?? DateTime.now();

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'] != null ? int.parse(json['id'].toString()) : null,
      nombre: json['nombre'],
      correo: json['correo'],
      contra: json['contra'],
      createdAt: json['created_at'] != null
          ? DateTime.parse(json['created_at'])
          : null,
      updatedAt: json['updated_at'] != null
          ? DateTime.parse(json['updated_at'])
          : null,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'nombre': nombre,
      'correo': correo,
      'contra': contra,
      'created_at': createdAt.toIso8601String(),
      'updated_at': updatedAt?.toIso8601String(),
    };
  }

  User copyWith({
    int? id,
    String? nombre,
    String? correo,
    String? contra,
    DateTime? updatedAt,
  }) {
    return User(
      id: id ?? this.id,
      nombre: nombre ?? this.nombre,
      correo: correo ?? this.correo,
      contra: contra ?? this.contra,
      createdAt: createdAt,
      updatedAt: updatedAt ?? this.updatedAt,
    );
  }
}