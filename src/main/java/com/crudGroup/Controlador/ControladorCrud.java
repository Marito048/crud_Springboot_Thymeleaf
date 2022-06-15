package com.crudGroup.Controlador;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crudGroup.Modelo.Usuario;
import com.crudGroup.Modelo.UsuarioCrud;

@Controller
@RequestMapping("/")
public class ControladorCrud {
    @Autowired
    private UsuarioCrud usuarioCrud;

    @RequestMapping(value="", method=RequestMethod.GET)
    public String listaUsuarios(ModelMap mp) {
        mp.put("usuarios", usuarioCrud.findAll());
        return "/lista";
    }

    @RequestMapping(value="/nuevo", method=RequestMethod.GET)
    public String nuevo(ModelMap mp) {
        mp.put ("usuario", new Usuario());
        return "/nuevo";
    }
    @RequestMapping(value="/crear", method=RequestMethod.POST)
    public String crear(@Valid Usuario usuario, BindingResult bindingresult, ModelMap mp) {
        if (bindingresult.hasErrors()) {
            return "/nuevo";
        }else {
            usuarioCrud.save(usuario);
            mp.put("usuario", usuario);
            return "/creado";
        }
    }


    
    @RequestMapping(value="/creado", method=RequestMethod.POST)
    public String creado(@RequestParam("usuario") Usuario usuario){
        return "/creado";
    

    }
    @RequestMapping(value="/editar/{id}", method=RequestMethod.GET)
public String editar(@PathVariable("id") long id, ModelMap mp){
    mp.put("usuario", usuarioCrud.findById((int) id));
    return "/editar";
}
@RequestMapping(value="/actualizar", method=RequestMethod.POST)
public String actualizar(@Valid Usuario usuario, BindingResult bindingResult, ModelMap mp){
    if(bindingResult.hasErrors()){
        mp.put("usuarios", usuarioCrud.findAll());
    return "/lista";
    }
    Optional<Usuario> usuarios = usuarioCrud.findById(usuario.getId()); 
    usuario.setNombre(usuario.getNombre());
    usuario.setPassword(usuario.getPassword());
    usuario.setEmail(usuario.getEmail());
    usuarioCrud.save(usuario);
    mp.put("usuario", usuario);
    return "/actualizado";
}
 
@RequestMapping(value="/borrar/{id}", method=RequestMethod.GET)
public String borrar(@PathVariable("id") int id, ModelMap mp){
    usuarioCrud.deleteById (id);
    mp.put("usuarios", usuarioCrud.findAll());
    return "/lista";
}


   




    
}
