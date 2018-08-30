var mongoose = require('mongoose');
const User = require("../models/user");
const jwt = require("jsonwebtoken");

//get User profile
module.exports.getProfile = function(req, res){
  const id=req.params.userId;
  if (!id) {
    res.status(401).json({
      "message" : "UnauthorizedError: private profile",
      status:401
    });
  } else {
    User
      .findById(id)
      .exec(function(err, user) {
        res.status(200).json(
        {
            message: "Request successful",
            user:user,
            userId:id,
            status: 200
        })
      });
  }
};

module.exports.editProfile = function(req,res){
  User.findByIdAndUpdate(
    req.params.userId,
    {
      $set:{
        name:req.body.name,
        age:req.body.age,
        weight:req.body.weight,
        address:req.body.address
      }
    },
    {new: true},
    function(err,result){
    if(err){
      console.log(err);
      res.status(500).json({
        error:err,
        status:500
      });
    }else{
      console.log(result);
      res.status(200).json(
        {
          message:"Request successful",
          result,
          userId:req.params.userId,
          status:200,

        });
      //console.log(result);
    }
  });

};






