package database;

public enum SQL {
    

/******************************************************************************/    
        
    //ATENCION: AL CONSTRUIR Y EMBELLEZER EL STRING DE LA SENTENCIA
    //VIGILAR QUE SE INTRODUZCAN LOS ESPACIOS.
    
/******************************************************************************/
    //GESTION DE USUARIOS
    
    CHECK_CEDULA(
        "SELECT idPersona FROM persona WHERE cedula = ?"
    ),
    CHECK_EMAIL(
        "SELECT idPersona FROM usuario WHERE email = ?"
    ),
    CHECK_CEDULA_JURIDICA(
        "SELECT idPersonaJuridica FROM personaJuridica WHERE cedulaJuridica = ?"
    ),
    VERIFY_USER(
        "SELECT usr.idPersona FROM usuario AS usr"
                + " JOIN persona AS per ON usr.idPersona = per.idPersona"
                + " WHERE per.cedula = ? AND usr.contraseña = ?"
    ),
    CHECK_ADMIN(
      """
      SELECT idPersona FROM inspector
      WHERE idPersona = ? AND idPuesto = 0
      """      
    ),
    GET_ID_PERSONA(
        "SELECT idPersona FROM persona WHERE cedula = ?"
    ),
    GET_ID_INSPECTOR(
        "SELECT ins.idPersona FROM inspector AS ins"
                + " JOIN persona AS per ON ins.idPersona = per.idPersona"
                + " WHERE per.cedula = ?"
    ),
    CREATE_OBJECT_INSPECTOR(
        "SELECT * FROM inspector WHERE idPersona = ?"   
    ),
    CREATE_OBJECT_PERSONA(
        "SELECT * FROM persona WHERE idPersona = ?"
    ),
    CREATE_OBJECT_USUARIO(
        "SELECT * FROM usuario WHERE idPersona = ?"
    ),
    LOGIN(
        "UPDATE usuario SET enLinea = TRUE WHERE (usuario.idPersona) = ?"
    ),
    LOGOUT(
        "UPDATE usuario SET enLinea = FALSE WHERE (usuario.idPersona) = ?"
    ),

/*******************************************************************************/    
    
    //AUTORIZACION DE USUARIOS
    LIST_AUTORIZACION_PENDIENTE(
    """
        SELECT per.cedula, usr.email, catp.nombre AS puesto, catr.nombre AS region, cato.nombre AS oficina
            FROM persona AS per
            JOIN usuario AS usr ON per.idPersona = usr.idPersona
            JOIN inspector AS ins ON usr.idPersona = ins.idPersona
            JOIN catalogopuesto AS catp ON ins.idPuesto = catp.idPuesto
            JOIN catalogoregion AS catr ON ins.idRegion = catr.idRegion
            JOIN catalogooficina AS cato ON ins.idOficina = cato.idOficina
            WHERE usr.autorizado = "P";
    """        
    ),
    AUTORIZAR_USUARIO(
    """
        UPDATE usuario AS usr
        JOIN persona AS per ON per.idPersona =  usr.idPersona
        SET usr.autorizado = "S" 
        WHERE per.idPersona = ?;
    """  
    ),
    DENEGAR_USUARIO(
    """
        UPDATE usuario AS usr
        JOIN persona AS per ON per.idPersona =  usr.idPersona
        SET usr.autorizado = "N" 
        WHERE per.idPersona = ?;
    """        
    ),
    
/******************************************************************************/
    //LISTAR COMPARECENCIAS
    LIST_ALL_COMPARECENCIAS(
    "SELECT comp.codigoSILAC, au.nombreAudio, cat.tipoCaso, comp.ubicacion, comp.fecha"
                    + " FROM comparecencia AS comp"
                    + " JOIN catalogocaso AS cat ON comp.idCaso = cat.idCaso"
                    + " JOIN audioxcomparecencia AS axc ON comp.idComparecencia = axc.idComparecencia"
                    + " JOIN audio AS au ON axc.idAudio = au.idAudio"
                    + " GROUP BY comp.codigoSILAC, au.nombreAudio, cat.tipoCaso, comp.ubicacion, comp.fecha"
    ),
    LIST_COMPARECENCIAS_BY_REGION(
    """
        SELECT comp.codigoSILAC, au.nombreAudio, cat.tipoCaso, comp.ubicacion, comp.fecha
            FROM comparecencia AS comp
            JOIN catalogocaso AS cat ON comp.idCaso = cat.idCaso
            JOIN audioxcomparecencia AS axc ON comp.idComparecencia = axc.idComparecencia
            JOIN audio AS au ON axc.idAudio = au.idAudio
            JOIN inspectorxcomparecencia AS ixc ON comp.idComparecencia = ixc.idComparecencia
            JOIN inspector AS ins ON ins.idPersona = ixc.idPersona
            WHERE ins.idRegion = ?
            GROUP BY comp.codigoSILAC, au.nombreAudio, cat.tipoCaso, comp.ubicacion, comp.fecha
    """
    ),
    LIST_COMPARECENCIAS_BY_OFICINA(
    """
        SELECT comp.codigoSILAC, au.nombreAudio, cat.tipoCaso, comp.ubicacion, comp.fecha
            FROM comparecencia AS comp
            JOIN catalogocaso AS cat ON comp.idCaso = cat.idCaso
            JOIN audioxcomparecencia AS axc ON comp.idComparecencia = axc.idComparecencia
            JOIN audio AS au ON axc.idAudio = au.idAudio
            JOIN inspectorxcomparecencia AS ixc ON comp.idComparecencia = ixc.idComparecencia
            JOIN inspector AS ins ON ins.idPersona = ixc.idPersona
            WHERE ins.idOficina = ?
            GROUP BY comp.codigoSILAC, au.nombreAudio, cat.tipoCaso, comp.ubicacion, comp.fecha
    """
    ),
    LIST_COMPARECENCIAS_BY_AUTHOR(
    """
        SELECT comp.codigoSILAC, au.nombreAudio, cat.tipoCaso, comp.ubicacion, comp.fecha
            FROM comparecencia AS comp
            JOIN catalogocaso AS cat ON comp.idCaso = cat.idCaso
            JOIN audioxcomparecencia AS axc ON comp.idComparecencia = axc.idComparecencia
            JOIN audio AS au ON axc.idAudio = au.idAudio
            JOIN inspectorxcomparecencia AS ixc ON comp.idComparecencia = ixc.idComparecencia
            JOIN inspector AS ins ON ins.idPersona = ixc.idPersona
            WHERE ins.idPersona = ?
            GROUP BY comp.codigoSILAC, au.nombreAudio, cat.tipoCaso, comp.ubicacion, comp.fecha
    """
    ),
    LIST_COMPARECENCIAS_BY_ACCESS(
    """
        SELECT comp.codigoSILAC, au.nombreAudio, cat.tipoCaso, comp.ubicacion, comp.fecha
            FROM comparecencia AS comp
            JOIN catalogocaso AS cat ON comp.idCaso = cat.idCaso
            JOIN audioxcomparecencia AS axc ON comp.idComparecencia = axc.idComparecencia
            JOIN audio AS au ON axc.idAudio = au.idAudio
            JOIN inspectorxcomparecencia AS ixc ON comp.idComparecencia = ixc.idComparecencia
            WHERE ixc.idPersona = ?
            GROUP BY comp.codigoSILAC, au.nombreAudio, cat.tipoCaso, comp.ubicacion, comp.fecha;
    """
    ),
 
//SUBIR Y DESCARGAR DATOS
/*******************************************************************************/    
    
    //SUBIR
    UPLOAD_COMPARECENCIAS(
        """
        SELECT * FROM comparecencia 
        WHERE comparecencia.idComparecencia = ?
        """
    ),
    UPLOAD_AUDIOXCOMPARECENCIAS(
        """
        SELECT * FROM audioxcomparecencia  
        WHERE audioxcomparecencia.idComparecencia = ?
        """
    ),
    UPLOAD_AUDIO(
        """
        SELECT * FROM audio
        WHERE audio.idAudio = ?
        """
    ),
    UPLOAD_GESTIONANTE(
        """
        SELECT * FROM gestionante
        WHERE gestionante.idComparecencia = ?
        """
    ),
    UPLOAD_PERSONA(
        """
        SELECT * FROM persona
        WHERE persona.idPersona = ?
        """
    ),
    UPLOAD_PERSONA_JURIDICA(
        """
        SELECT * FROM personajuridica
        WHERE personajuridica.idPersonaJuridica = ?
        """
    ),
    UPLOAD_TESTIGO_GESTIONANTE(
        """
        SELECT * FROM testigo
        WHERE testigo.idGestionante = ?
        """
    ),
    UPLOAD_ACOMPAÑANTE_GESTIONANTE(
        """
        SELECT * FROM acompañante
        WHERE acompañante.idGestionante = ?
        """
    ),
    UPLOAD_REPRESENTANTE_GESTIONANTE(
        """
        SELECT * FROM representante
        WHERE representante.idGestionante = ?
        """
    ),
    UPLOAD_GESTIONADO(
        """
        SELECT * FROM gestionado
        WHERE gestionado.idComparecencia = ?
        """
    ),
    UPLOAD_TESTIGO_GESTIONADO(
        """
        SELECT * FROM testigo
        WHERE testigo.idGestionado = ?
        """
    ),
    UPLOAD_ACOMPAÑANTE_GESTIONADO(
        """
        SELECT * FROM acompañante
        WHERE acompañante.idGestionado = ?
        """
    ),
    UPLOAD_REPRESENTANTE_GESTIONADO(
        """
        SELECT * FROM representante
        WHERE representante.idGestionado = ?
        """
    ),
    UPLOAD_INSPECTORXCOMPARECENCIAS(
        """
        SELECT * FROM inspectorxcomparecencia  
        WHERE inspectorxcomparecencia.idComparecencia = ?
        """
    ),
    UPLOAD_INSPECTOR(
        """
        SELECT * FROM inspector
        WHERE inspector.idPersona = ?         
        """
    ),
    UPLOAD_USUARIO(
        """
        SELECT * FROM usuario
        WHERE usuario.idPersona = ?
        """
    ),
    
    
    //DESCARGAR
    
    INSERT_ACOMPAÑANTE(
        "INSERT INTO acompañante(idPersona,idGestionante,idGestionado,tipoAcompañante,enCondicionDe)VALUES(?,?,?,?,?)"
    ),
    INSERT_AUDIO(
        "INSERT INTO audio(idAudio,nombreAudio,duracionAudio,pathArchivoAudio,pathArchivoAnotaciones)VALUES(?,?,?,?,?)"
    ),
    INSERT_AUDIOXCOMPARECENCIA(
        "INSERT INTO audioxcomparecencia(idAudio,idComparecencia)VALUES(?,?)"
    ),
    INSERT_CASO(
        "INSERT INTO catalogocaso(idCaso,tipoCaso)VALUES(?,?)"
    ),
    INSERT_OFICINA(
        "INSERT INTO catalogooficina(idOficina,nombre)VALUES(?,?)"    
    ),
    INSERT_PUESTO(
        "INSERT INTO catalogopuesto(idPuesto,nombre)VALUES(?,?)"
    ),
    INSERT_REGION(
        "INSERT INTO catalogoregion(idRegion,nombre)VALUES(?,?)"
    ),
    INSERT_COMPARECENCIA(
        "INSERT INTO comparecencia(idComparecencia,codigoSILAC,idCaso,ubicacion,linkExpediente,fecha)VALUES(?,?,?,?,?,?)"
    ),
    INSERT_GESTIONADO(
        "INSERT INTO gestionado(idGestionado,idPersona,idPersonaJuridica,idComparecencia)VALUES(?,?,?,?)"
    ),
    INSERT_GESTIONANTE(
        "INSERT INTO gestionante(idGestionante,idPersona,idPersonaJuridica,idComparecencia)VALUES(?,?,?,?)"
    ),
    INSERT_INSPECTOR(
        "INSERT INTO inspector(idPersona,idPuesto,idRegion,idOficina)VALUES(?,?,?,?)"
    ),
    INSERT_INSPECTORXCOMPARECENCIA(
        "INSERT INTO inspectorxcomparecencia(idPersona,idComparecencia)VALUES(?,?)"
    ),
    INSERT_PERSONA(
        "INSERT INTO persona(idPersona,cedula,primerNombre,segundoNombre,primerApellido,segundoApellido)VALUES(?,?,?,?,?,?)"
    ),
    INSERT_PERSONAJURIDICA(
        "INSERT INTO personajuridica(idPersonaJuridica,cedulaJuridica,nombreRazonSocial)VALUES(?,?,?)"
    ),
    INSERT_REPRESENTANTE(
        "INSERT INTO representante(idPersona,idGestionante,idGestionado)VALUES(?,?,?)"
    ),
    INSERT_TESTIGO(
        "INSERT INTO testigo(idPersona,idGestionante,idGestionado)VALUES(?,?,?)"
    ),
    INSERT_USUARIO(
        "INSERT INTO usuario(idPersona,email,contraseña,enLinea)VALUES(?,?,?,?)"
    ),
      
    
    //OTROS
    GET_ID_COMPARECENCIA_BY_SILAC(
    """
        SELECT idComparecencia FROM comparecencia WHERE codigoSILAC = ?
    """        
    ),
    GET_AUDIO_RELATED_COMPARECENCIA(
        """
        SELECT au.idAudio FROM audio AS au
        JOIN audioxcomparecencia AS axc ON au.idAudio = axc.idAudio 
        JOIN comparecencia AS comp ON comp.idComparecencia
        WHERE comp.idComparecencia = ?
        AND axc.idComparecencia = ?
        """
    ),
    GET_AUDIOPATH_BY_PATH(
            """
        SELECT au.pathArchivoAudio FROM audio AS au
        JOIN audioxcomparecencia AS axc ON au.idAudio = axc.idAudio 
        JOIN comparecencia AS comp ON comp.idComparecencia
        WHERE comp.idComparecencia = ?
        AND au.pathArchivoAudio LIKE ?
    """),
    GET_ANOTACIONPATH_BY_PATH(
    """
        SELECT au.pathArchivoAnotaciones FROM audio AS au
        JOIN audioxcomparecencia AS axc ON au.idAudio = axc.idAudio 
        JOIN comparecencia AS comp ON comp.idComparecencia
        WHERE comp.idComparecencia = ?
        AND au.pathArchivoAnotaciones LIKE ?                       
    """),
    
/******************************************************************************/
    //OTROS
    GET_USUARIOS_ENLINEA(
       """
       SELECT COUNT(enLinea) FROM usuario
       WHERE enLinea = TRUE;
       """ 
    ),
    BUSQUEDA_DATOS_TABLA(
        "SELECT comp.codigoSILAC, au.nombreAudio, cat.tipoCaso, comp.ubicacion, comp.fecha"
                    + " FROM comparecencia AS comp"
                    + " JOIN catalogocaso AS cat ON comp.idCaso = cat.idCaso"
                    + " JOIN audioxcomparecencia AS axc ON comp.idComparecencia = axc.idComparecencia"
                    + " JOIN audio AS au ON axc.idAudio = au.idAudio"
                    + " WHERE ([A] LIKE "+"\"%"+"[B]"+"%\")"
                    + " AND (comp.fecha BETWEEN \"[C]\" AND \"[D]\")"
                    + " GROUP BY comp.codigoSILAC"
                    + " ORDER BY [A] [E]"
    ),
    ID_LAST_ROW_ACOMPAÑANTE(
        "SELECT MAX(idPersona) FROM acompañante"
    ),
    ID_LAST_ROW_AUDIO(
        "SELECT MAX(idAudio) FROM audio"
    ),
    ID_LAST_ROW_COMPARECENCIA(
        "SELECT MAX(idComparecencia) FROM comparecencia"
    ),
    ID_LAST_ROW_GESTIONADO(
        "SELECT MAX(IdGestionado) FROM gestionado"
    ),
    ID_LAST_ROW_GESTIONANTE(
        "SELECT MAX(IdGestionante) FROM gestionante"
    ),
    ID_LAST_ROW_INSPECTOR(
        "SELECT MAX(idPersona) FROM inspector"
    ),
    ID_LAST_ROW_PERSONA(
        "SELECT MAX(idPersona) FROM persona"
    ),
    ID_LAST_ROW_PERSONAJURIDICA(
        "SELECT MAX(idPersonaJuridica) FROM personajuridica"
    ),
    ID_LAST_ROW_REPRESENTANTE(
        "SELECT MAX(idPersona) FROM representante"
    ),
    ID_LAST_ROW_TESTIGO(
        "SELECT MAX(idPersona) FROM testigo"
    ),
    ID_LAST_ROW_USUARIO(
        "SELECT MAX(idPersona) FROM usuario"
    );
    
/******************************************************************************/
    
    private final String display;

    private SQL(String s) {
        display = s;
    }

    @Override
    public String toString() {
        return display;
    } 
}

/*
UNIVERSIDAD ESTATAL A DISTANCIA
VICERRECTORIA ACADÉMICA 
ESCUELA DE CIENCIAS EXACTAS Y NATURALES 
CARRERA INGENIERÍA INFORMÁTICA 

Desarrollar una aplicación de escritorio
Para la administración de comparecencias del
Ministerio de Trabajo y Seguridad Social de la
Región Huetar Caribe

MODALIDAD ESCOGIDA: PROYECTO

PARTE PROGRAMADA
PARA OPTAR POR EL TÍTULO DE 
BACHILLER EN INGENIERÍA INFORMÁTICA 

PROPRIETARIO:
MOISES ROMERO PRADO
CEDULA 303370265

AUTORES:
ROBERT JESÚS CASCANTE ARAYA,
CÉDULA 305180118
CORREO jesuscascantearaya@gmail.com
TELEFONO 88943263
DAYRON ANTONY CHAVES SANDOVAL,
CÉDULA 305240018 
TELEFONO 83959225
CORREO dayron.chaves@pm.me

CENTRO UNIVERSITARIO DE TURRIALBA
PAC 2023-1
TURRIALBA, 2023  
*/